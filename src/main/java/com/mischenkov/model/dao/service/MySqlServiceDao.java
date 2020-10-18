package com.mischenkov.model.dao.service;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.model.dao.CommonFunctionalityDao;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mischenkov.controller.RequestDecoder.decodeRequest;

public class MySqlServiceDao implements ExtendedServiceDao {

    private static final Logger LOG = Logger.getLogger(MySqlServiceDao.class);

    // CRUD operation
    private static final String SQL_DELETE_SERVICE = "DELETE FROM `services` WHERE `service_id` = ?";
    // common operation
    private static final String SQL_SELECT_ALL_SERVICE = "SELECT `service_id`, `active`, `start_date`, `end_date`, " +
            "`lang_serv`.`title` as `title`, `lang_serv`.`description` as `description`, `lang_serv`.`short_description` as `short_description` " +
            "FROM `services` " +
            "JOIN `lang_serv` ON `lang_serv`.`serv_id` = `services`.`service_id` " +
            "WHERE `lang_serv`.`lang_id` = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT `service_id`, `active`, `start_date`, `end_date`, " +
            "`lang_serv`.`title` as `title`, `lang_serv`.`description` as `description`, `lang_serv`.`short_description` as `short_description` " +
            "FROM `services` " +
            "JOIN `lang_serv` ON `lang_serv`.`serv_id` = `services`.`service_id` " +
            "WHERE `lang_serv`.`lang_id` = ? AND `service_id` = ?";
    private static final String SQL_UPDATE_SERVICE_ACTIVE = "UPDATE `services` SET `active` = ? WHERE `service_id` = ?";
    // insert service
    private static final String SQL_INSERT_SERVICE = "INSERT INTO `services` (`active`, `start_date`, `end_date`) VALUES (?, ?, ?)";
    private static final String SQL_INSERT_SERVICE_LANGUAGE = "INSERT INTO `lang_serv` (`lang_id`, `serv_id`, `title`, `short_description`, `description`) VALUES (?, ?, ?, ?, ?)";

    @Override
    public List<Service> getAll(Connection con, Language language) throws SQLException {
        Objects.requireNonNull(con, "getAll(Connection con, Language language), \"con\" is null.");
        Objects.requireNonNull(con, "getAll(Connection con, Language language), \"language\" is null.");

        List<Service> serviceList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_ALL_SERVICE);
            preparedStatement.setInt(1,  language.getId());

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<List<Service>> obtainer = new ServiceListObtainer();
            serviceList.addAll(
                    obtainer.obtain(resultSet)
            );

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return serviceList;
    }

    @Override
    public void updateServiceActive(Connection con, int serviceId, int active) throws SQLException {
        Objects.requireNonNull(con, "updateServiceActive(Connection con, int serviceId, int active), \"con\" is null.");

        CommonFunctionalityDao.updateEntityActiveState(con, serviceId, active, SQL_UPDATE_SERVICE_ACTIVE);
    }

    @Override
    public void saveService(Connection con, Map<Integer, Service> langIdOnService) throws SQLException {
        Objects.requireNonNull(con, "saveService(Connection con, Map<Integer, Service> langIdOnService), \"con\" is null");
        Objects.requireNonNull(langIdOnService, "saveService(Connection con, Map<Integer, Service> langIdOnService), \"langIdOnService\" is null");

        Service service = langIdOnService.values()
                .stream()
                .findFirst()
                .get();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // insert service
            preparedStatement = con.prepareStatement(SQL_INSERT_SERVICE, Statement.RETURN_GENERATED_KEYS);
            String startDate = service.getStartDate();
            String endDate = service.getEndDate();
            int active = service.isActive() ? 1 : 0;

            preparedStatement.setInt(1, active);
            preparedStatement.setString(2, startDate);
            preparedStatement.setString(3, endDate);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            int serviceKey = -1;

            if ( resultSet.next() ) {
                serviceKey = resultSet.getInt(1);
            } else {
                LOG.warn("Cant obtain valid KEY");
                throw new SQLException("saveService(Connection con, Map<Integer, Service> langIdOnService), ant obtain valid KEY.");
            }

            // insert language
            for (Integer languageKey: langIdOnService.keySet()) {

                preparedStatement = con.prepareStatement(SQL_INSERT_SERVICE_LANGUAGE);

                int k = 1;

                preparedStatement.setInt(k++, languageKey);
                preparedStatement.setInt(k++, serviceKey);

                String title = langIdOnService.get(languageKey).getTitle();
                preparedStatement.setString(k++, decodeRequest(title) );

                String shortDescription = langIdOnService.get(languageKey).getShortDescription();
                preparedStatement.setString(k++, decodeRequest(shortDescription) );

                String description = langIdOnService.get(languageKey).getDescription();
                preparedStatement.setString(k++, decodeRequest(description) );

                preparedStatement.executeUpdate();
            }

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public List<Service> getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Service getById(Connection con, Service entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Service getById(Connection con, Service entity, Language language) throws SQLException {
        Objects.requireNonNull(con, "getById(Connection con, Service entity), \"con\" is null.");
        Objects.requireNonNull(entity, "getById(Connection con, Service entity), \"entity\" is null.");
        Objects.requireNonNull(language, "getById(Connection con, Service entity), \"language\" is null.");

        Service service = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            int languageId = language.getId();
            int serviceId = entity.getId();

            preparedStatement = con.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, languageId);
            preparedStatement.setInt(2, serviceId);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<Service> obtainer = new ServiceObtainer();
            service = obtainer.obtain( resultSet );

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return service;
    }

    @Override
    public int save(Connection con, Service entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Service entity) throws SQLException {
        Objects.requireNonNull(con, "delete(Connection con, Service entity), \"con\" is null");
        Objects.requireNonNull(entity, "delete(Connection con, Service entity), \"entity\" is null");

        int id = entity.getId();
        CommonFunctionalityDao.deleteEntity(con, id, SQL_DELETE_SERVICE);
    }

    @Override
    public int count(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }
}
