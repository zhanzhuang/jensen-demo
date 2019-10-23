package spring.dao.impl;

import spring.dao.IAccountDao;

public class IAccountDaoImpl implements IAccountDao {
    @Override
    public void saveAccount() {
        System.out.println("save...");
    }
    public IAccountDaoImpl() {
        System.out.println("IAccountDaoImpl initialized");
    }
}
