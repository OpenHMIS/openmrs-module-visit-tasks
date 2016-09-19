package org.openmrs.module.visittasks.api.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.f.Action1;
import org.openmrs.module.visittasks.api.IVisitPredefinedTaskDataService;
import org.openmrs.module.visittasks.api.model.VisitPredefinedTask;
import org.openmrs.module.visittasks.api.security.BasicMetadataAuthorizationPrivileges;
import org.openmrs.module.visittasks.api.util.VisitTasksHibernateCriteriaConstants;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data service implementation class for {@link VisitPredefinedTask}s.
 */
@Transactional
public class VisitPredefinedTaskDataServiceImpl extends BaseMetadataDataServiceImpl<VisitPredefinedTask> implements
        IVisitPredefinedTaskDataService {
	private static final Integer MAX_PREDEFINED_TASK_NAME_CHARACTERS = 255;

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return new BasicMetadataAuthorizationPrivileges();
	}

	@Override
	protected void validate(VisitPredefinedTask entity) {}

	@Override
	public List<VisitPredefinedTask> getPredefinedTasksByUser(Integer userId, boolean includeRetired) {
		return getPredefinedTasksByUser(userId, includeRetired, null);
	}

	@Override
	public List<VisitPredefinedTask> getPredefinedTasksByUser(final Integer userId, final boolean includeRetired,
	        PagingInfo pagingInfo) {
		if (userId == null) {
			throw new IllegalArgumentException("User must be logged in");
		}

		return executeCriteria(VisitPredefinedTask.class, pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				/*criteria.add(Restrictions.eq(VisitTasksHibernateCriteriaConstants.USER, userId));
				if (!includeRetired) {
					criteria.add(Restrictions.eq(VisitTasksHibernateCriteriaConstants.RETIRED, false));
				}
				criteria.add(Restrictions.or(VisitTasksHibernateCriteriaConstants.GLOBAL,true));*/

				Criterion userCriterion = Restrictions.eq(VisitTasksHibernateCriteriaConstants.USER, userId);
				Criterion globalCriterion = Restrictions.eq(VisitTasksHibernateCriteriaConstants.GLOBAL, true);

				criteria.add(Restrictions.or(userCriterion, globalCriterion));
			}
		});
	}

	@Override
	public List<VisitPredefinedTask> getPredefinedTasksByUserAndName(Integer userId, String name, boolean includeRetired) {
		return getPredefinedTasksByUserAndName(userId, name, includeRetired, null);
	}

	@Override
	public List<VisitPredefinedTask> getPredefinedTasksByUserAndName(final Integer userId, final String name,
	        final boolean includeRetired,
	        PagingInfo pagingInfo) {
		if (userId == null) {
			throw new IllegalArgumentException("User must be logged in");
		}
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("The Predefined task name must be defined.");
		}
		if (name.length() > MAX_PREDEFINED_TASK_NAME_CHARACTERS) {
			throw new IllegalArgumentException("The Predefined task name must be less than 256 characters.");
		}

		return executeCriteria(VisitPredefinedTask.class, pagingInfo, new Action1<Criteria>() {
			@Override
			public void apply(Criteria criteria) {
				criteria.add(Restrictions.eq(VisitTasksHibernateCriteriaConstants.USER, userId)).add(
				    Restrictions.ilike(VisitTasksHibernateCriteriaConstants.NAME, name, MatchMode.START));

				if (!includeRetired) {
					criteria.add(Restrictions.eq(VisitTasksHibernateCriteriaConstants.RETIRED, false));
				}
			}
		});
	}
}
