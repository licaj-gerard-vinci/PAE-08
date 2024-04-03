package be.vinci.pae.business.manager;

import be.vinci.pae.dal.DALServices;
import be.vinci.pae.dal.manager.ManagerDAO;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.exceptions.NotFoundException;
import jakarta.inject.Inject;

import java.util.List;

public class ManagerUCCImpl implements ManagerUCC {

    @Inject
    private DALServices dalServices;
    @Inject
    private ManagerDAO managerDAO;

    @Override
    public List<ManagerDTO> getManagers(int companyId) {
        try {
            dalServices.startTransaction();
            List<ManagerDTO> manager = managerDAO.getManagers(companyId);
            if (manager == null) {
                throw new NotFoundException("Manager not found");
            }
            dalServices.commitTransaction();
            return manager;
        } catch (FatalException e) {
            dalServices.rollbackTransaction();
            throw e;
        }
    }

}
