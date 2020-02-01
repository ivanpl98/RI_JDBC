package uo.ri.business.impl;

import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.administrator.mechanic.*;

import java.util.List;

public class MechanicCrudServiceImpl implements MechanicCrudService {
    @Override
    public void addMechanic(MechanicDto mecanico) throws BusinessException {
        AddMechanic am = new AddMechanic(mecanico);
        am.execute();
    }

    @Override
    public void deleteMechanic(Long idMecanico) throws BusinessException {
        DeleteMechanic dm = new DeleteMechanic(idMecanico);
        dm.execute();
    }

    @Override
    public void updateMechanic(MechanicDto mecanico) throws BusinessException {
        UpdateMechanic um = new UpdateMechanic(mecanico);
        um.execute();
    }

    @Override
    public MechanicDto findMechanicById(Long id) throws BusinessException {
        FindByID fm = new FindByID(id);
        return fm.excecute();
    }

    @Override
    public List<MechanicDto> findAllMechanics() throws BusinessException {
        ListMechanics lm = new ListMechanics();
        return lm.execute();
    }

}
