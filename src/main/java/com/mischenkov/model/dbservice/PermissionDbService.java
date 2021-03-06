package com.mischenkov.model.dbservice;

import com.mischenkov.entity.Permission;
import com.mischenkov.model.exception.DBException;

/**
 *  Interface for working with restrictions.
 */
public abstract class PermissionDbService extends BaseDbService {

    abstract Permission getByRoleId(int roleId) throws DBException;

}
