package be.vinci.pae.business.manager;


import java.util.List;

public interface ManagerUCC {
    List<ManagerDTO> getManagers(int companyId);
}
