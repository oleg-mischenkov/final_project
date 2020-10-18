package com.mischenkov.model.dao.tariff;

import com.mischenkov.entity.Language;
import com.mischenkov.entity.Service;
import com.mischenkov.entity.Tariff;
import com.mischenkov.model.dao.CommonFunctionalityDao;
import com.mischenkov.model.dao.Dao;
import com.mischenkov.model.dao.EntityObtainer;
import com.mischenkov.model.dao.SqlOrderBy;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mischenkov.controller.RequestDecoder.decodeRequest;

public class MySqlTariffDao implements ExtendedTariffDao {

    private static final Logger LOG = Logger.getLogger(MySqlTariffDao.class);

    // CRUD operation
    private static final String SQL_DELETE_TARIFF = "DELETE FROM `tariffs` WHERE `tariff_id` = ?";
    // extended operation
    private static final String SQL_UPDATE_TARIFF_ACTIVE = "UPDATE `tariffs` SET `active` = ? WHERE `tariff_id` = ?;";
    private static final String SQL_SELECT_TARIFF_COUNT = "SELECT COUNT(*) as `count` FROM `tariffs`";
    private static final String SQL_SELECT_TARIFF_COUNT_FOR_SERVICE = "SELECT COUNT(*) as `count` FROM `tariffs` WHERE `service_id` = ?";
    private static final String SQL_SELECT_ALL_TARIFFS_BY_SERVICE_ID_AND_SORT = "SELECT  " +
            "`tariff_id`, `active`, `price`, `start_date`, `end_date`, `lang_tariffs`.`title` as `title`, " +
            "`lang_tariffs`.`description` as `description`, `lang_tariffs`.`short_description` as `short_description` " +
            "FROM `tariffs` " +
            "JOIN `lang_tariffs` " +
            "ON `lang_tariffs`.`trf_id` = `tariffs`.`tariff_id` WHERE `lang_tariffs`.`lang_id` = ? AND `service_id` = ? " +
            "ORDER BY %s %s LIMIT ?, ?";
    private static final String SQL_SELECT_TARIFF_BY_ID = "SELECT " +
            "`tariff_id`, `active`, `price`, `start_date`, `end_date`, `lang_tariffs`.`title` as `title`, " +
            "`lang_tariffs`.`description` as `description`, `lang_tariffs`.`short_description` as `short_description` " +
            "FROM `tariffs` " +
            "JOIN `lang_tariffs` ON `lang_tariffs`.`trf_id` = `tariffs`.`tariff_id` " +
            "WHERE `lang_tariffs`.`lang_id` = ? AND `tariff_id` = ?";

    // insert tariff
    private static final String SQL_INSERT_TARIFF = "INSERT INTO `tariffs` (`active`, `price`, `start_date`, `end_date`, `service_id`) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_TARIFF_LANGUAGE = "INSERT INTO `lang_tariffs` (`lang_id`, `trf_id`, `title`, `short_description`, `description`) VALUES (?, ?, ?, ?, ?)";

    @Override
    public Tariff getById(Connection con, Language language, int tariffId) throws SQLException {
        Objects.requireNonNull(con, "getById(Connection con, Language language, int tariffId), \"con\" is null.");
        Objects.requireNonNull(language, "getById(Connection con, Language language, int tariffId), \"language\" is null.");

        Tariff tariff = null;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_TARIFF_BY_ID);

            int languageId = language.getId();

            preparedStatement.setInt(1, languageId);
            preparedStatement.setInt(2, tariffId);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<Tariff> obtainer = new TariffObtainer();
            tariff = obtainer.obtain(resultSet);

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return tariff;
    }

    @Override
    public List<Tariff> getAll(Connection con, Language language, int serviceId) throws SQLException {

        int startSortPosition = 0;

        return getAll(
                con,
                language, serviceId,
                "tariff_id", SqlOrderBy.ASC,
                startSortPosition, count(con)
        );
    }

    @Override
    public List<Tariff> getAll(Connection con, Language language, int serviceId, String sortField, SqlOrderBy orderBy, int position, int count) throws SQLException {
        Objects.requireNonNull(con, "getAll(Connection con, Language language, int serviceId, String sortField, SqlOrderBy orderBy), \"con\" is null.");
        Objects.requireNonNull(language, "getAll(Connection con, Language language, int serviceId, String sortField, SqlOrderBy orderBy), \"language\" is null.");
        Objects.requireNonNull(sortField, "getAll(Connection con, Language language, int serviceId, String sortField, SqlOrderBy orderBy), \"sortField\" is null.");
        Objects.requireNonNull(orderBy, "getAll(Connection con, Language language, int serviceId, String sortField, SqlOrderBy orderBy), \"orderBy\" is null.");

        List<Tariff> tariffList = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = String.format(SQL_SELECT_ALL_TARIFFS_BY_SERVICE_ID_AND_SORT, sortField, orderBy.name() );

            LOG.debug(sqlQuery);

            preparedStatement = con.prepareStatement( sqlQuery );
            preparedStatement.setInt(1, language.getId());
            preparedStatement.setInt(2, serviceId);
            preparedStatement.setInt(3, position);
            preparedStatement.setInt(4, count);

            resultSet = preparedStatement.executeQuery();

            EntityObtainer<List<Tariff>> obtainer = new TariffListObtainer();
            tariffList.addAll(
                    obtainer.obtain(resultSet)
            );

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return tariffList;
    }

    @Override
    public void updateTariffActive(Connection con, int tariffId, int active) throws SQLException {
        Objects.requireNonNull(con, "updateServiceActive(Connection con, int tariffId, int active), \"con\" is null.");

        CommonFunctionalityDao.updateEntityActiveState(con, tariffId, active, SQL_UPDATE_TARIFF_ACTIVE);
    }

    @Override
    public void saveTariff(Connection con, Map<Integer, Tariff> langIdOnTariff, int serviceId) throws SQLException {
        Objects.requireNonNull(con, "saveService(Connection con, Map<Integer, Tariff> langIdOnTariff, int serviceId), \"con\" is null");
        Objects.requireNonNull(langIdOnTariff, "saveService(Connection con, Map<Integer, Tariff> langIdOnService, int serviceId), \"langIdOnTariff\" is null");

        Tariff tariff = langIdOnTariff.values()
                .stream()
                .findFirst()
                .get();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_INSERT_TARIFF, Statement.RETURN_GENERATED_KEYS);

            int active = tariff.isActive() ? 1 : 0;
            int price = tariff.getPrice();
            String startDate = tariff.getStartDate();
            String endDate = tariff.getEndDate();

            preparedStatement.setInt(1, active);
            preparedStatement.setInt(2, price);
            preparedStatement.setString(3, startDate);
            preparedStatement.setString(4, endDate);
            preparedStatement.setInt(5, serviceId);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            int tariffKey = EntityObtainer.obtainKey(resultSet);

            // insert language
            for (Integer languageKey: langIdOnTariff.keySet()) {

                preparedStatement = con.prepareStatement(SQL_INSERT_TARIFF_LANGUAGE);

                Tariff currentTariff = langIdOnTariff.get(languageKey);

                String title = currentTariff.getTitle();
                String shortDescription = currentTariff.getShortDescription();
                String description = currentTariff.getDescription();

                int k = 1;

                preparedStatement.setInt(k++, languageKey);
                preparedStatement.setInt(k++, tariffKey);
                preparedStatement.setString(k++, decodeRequest(title) );
                preparedStatement.setString(k++, decodeRequest(shortDescription) );
                preparedStatement.setString(k++, decodeRequest(description) );

                preparedStatement.executeUpdate();
            }

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }
    }

    @Override
    public int count(Connection con, Service service) throws SQLException {
        Objects.requireNonNull(con, "count(Connection con, Service service), \"con\" is null");
        Objects.requireNonNull(service, "count(Connection con, Service service), \"service\" is null");

        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = con.prepareStatement(SQL_SELECT_TARIFF_COUNT_FOR_SERVICE);
            preparedStatement.setInt(1, service.getId());

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            count = resultSet.getInt("count");

        } finally {
            Dao.quiteClose(resultSet);
            Dao.quiteClose(preparedStatement);
        }

        return count;
    }

    @Override
    public List<Tariff> getAll(Connection con) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Tariff getById(Connection con, Tariff entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int save(Connection con, Tariff entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Connection con, Tariff entity) throws SQLException {
        Objects.requireNonNull(con, "delete(Connection con, Tariff entity), \"con\" is null");
        Objects.requireNonNull(entity, "delete(Connection con, Tariff entity), \"entity\" is null");

        int id = entity.getId();

        CommonFunctionalityDao.deleteEntity(con, id, SQL_DELETE_TARIFF);
    }

    @Override
    public int count(Connection con) throws SQLException {
        Objects.requireNonNull(con, "count(Connection con), \"con\" is null.");

        return CommonFunctionalityDao.count(con, SQL_SELECT_TARIFF_COUNT);
    }
}
