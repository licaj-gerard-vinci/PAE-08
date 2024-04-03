package be.vinci.pae.dal.manager;

import be.vinci.pae.business.manager.ManagerDTO;

import java.util.List;

public interface ManagerDAO {
    List<ManagerDTO> getManagers(int companyId);
}
