package paoo.cappuccino.ucc.impl;

import java.time.LocalDateTime;

import paoo.cappuccino.business.dto.IBusinessDayDto;
import paoo.cappuccino.business.dto.ICompanyDto;
import paoo.cappuccino.business.dto.IParticipationDto;
import paoo.cappuccino.business.dto.IParticipationDto.State;
import paoo.cappuccino.business.entity.factory.IEntityFactory;
import paoo.cappuccino.core.injector.Inject;
import paoo.cappuccino.dal.IDalService;
import paoo.cappuccino.dal.dao.IBusinessDayDao;
import paoo.cappuccino.ucc.IBusinessDayUcc;
import paoo.cappuccino.util.ValidationUtil;

class BusinessDayUcc implements IBusinessDayUcc {

  private final IEntityFactory factory;
  private final IBusinessDayDao dao;

  @Inject
  public BusinessDayUcc(IEntityFactory entityFactory, IDalService dalService,
      IBusinessDayDao businessDayDao) {
    this.factory = entityFactory;
    this.dao = businessDayDao;
  }

  @Override
  public IBusinessDayDto create(LocalDateTime eventDate) {
    ValidationUtil.ensureNotNull(eventDate, "evenDate");
    IBusinessDayDto dto = factory.createBusinessDay(eventDate);
    IBusinessDayDto returnedDto = null;
    if (dao.fetchBusinessDaysByDate(eventDate.getYear()) != null) {
      throw new IllegalArgumentException("A business day already exists for this year");
    }
    returnedDto = dao.createBusinessDay(dto);
    return returnedDto;
  }

  @Override
  public ICompanyDto[] addInvitedCompanies(ICompanyDto[] companies, IBusinessDayDto businessDay) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean changeState(IParticipationDto participation, State state) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public IBusinessDayDto[] getInvitationlessDays() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IBusinessDayDto[] getBusinessDays() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ICompanyDto[] getAttendingCompanies(int businessDayId) {
    // TODO Auto-generated method stub
    return null;
  }

}
