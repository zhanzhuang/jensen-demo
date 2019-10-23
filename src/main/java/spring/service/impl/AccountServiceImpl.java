package spring.service.impl;

import spring.dao.IAccountDao;
import spring.dao.impl.IAccountDaoImpl;
import spring.service.IAccountService;

public class AccountServiceImpl implements IAccountService {
    private IAccountDao accountDao = new IAccountDaoImpl();

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }

    public AccountServiceImpl() {
        System.out.println("AccountServiceImpl initialized");
    }
}
