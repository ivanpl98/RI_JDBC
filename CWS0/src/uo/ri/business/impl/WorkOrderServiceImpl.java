package uo.ri.business.impl;

import uo.ri.business.WorkOrderService;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;

import java.util.List;
import java.util.Optional;

public class WorkOrderServiceImpl implements WorkOrderService {
    @Override
    public WorkOrderDto registerNew(WorkOrderDto dto) throws BusinessException {
        return null;
    }

    @Override
    public void updateWorkOrder(WorkOrderDto dto) throws BusinessException {

    }

    @Override
    public void deleteWorkOrder(Long id) throws BusinessException {

    }

    @Override
    public Optional<WorkOrderDto> findWorkOrderById(Long woId) throws BusinessException {
        return Optional.empty();
    }

    @Override
    public List<WorkOrderDto> findUnfinishedWorkOrders() throws BusinessException {
        return null;
    }

    @Override
    public List<WorkOrderDto> findWorkOrdersByVehicleId(Long id) throws BusinessException {
        return null;
    }

    @Override
    public List<WorkOrderDto> findWorkOrdersByPlateNumber(String plate) throws BusinessException {
        return null;
    }

    @Override
    public List<CertificateDto> findCertificatesByVehicleTypeId(Long id) throws BusinessException {
        return null;
    }

    @Override
    public void assignWorkOrderToMechanic(Long woId, Long mechanicId) throws BusinessException {

    }
}
