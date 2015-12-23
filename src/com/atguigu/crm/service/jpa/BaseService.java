package com.atguigu.crm.service.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.atguigu.crm.dao.jpa.BaseRepository;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.ReflectionUtils;

public class BaseService<T, PK extends Serializable> {

	@Autowired
	private BaseRepository<T, PK> baseRepository;

	public Page<T> getList(int pageNo, int pageSize, Map<String, Object> params) {
		PageRequest pageable = new PageRequest(pageNo,pageSize);
		
		List<PropertyFilter> filters = PropertyFilter.parseParamsToFilters(params);
		
		Specification<T> soecification = parsePropertyFilterToSpecification(filters);
		
		return baseRepository.findAll(soecification,pageable);
	}

	private Specification<T> parsePropertyFilterToSpecification(
			final List<PropertyFilter> filters) {
		
		Specification<T> specification =new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = null;
				if(filters.size() > 0 && filters != null){
					List<Predicate> predicates = new ArrayList<>();
					for (PropertyFilter filter : filters) {
						
						
						String propertyName = filter.getPropertyName();
						String[] split = propertyName.split("//.");
						Path expression = root.get(split[0]);
						if(split.length > 1)
						{
							for(int i = 1;i < split.length;i++)
							{
								expression.get(split[i]);
							}
						}
						MatchType matchType = filter.getMatchType();
						Object propertyVal = filter.getPropertyVal();
						Class propertyType = filter.getPropertyType();
						propertyVal = ReflectionUtils.convertValue(propertyVal, propertyType);
						switch (matchType) {
						case EQ:
							predicate = builder.equal(expression, propertyVal);
							break;
						case GE:
							predicate = builder.ge(expression, (Number)propertyVal);
							break;
						case GT:
							predicate = builder.gt(expression, (Number)propertyVal);
							break;
						case LE:
							predicate = builder.le(expression, (Number)propertyVal);
							break;
						case LT:
							predicate = builder.lt(expression, (Number)propertyVal);
							break;
						case LIKE:
							predicate = builder.like(expression, "%" + propertyVal + "%");
							break;
						case ISNULL:
							predicate = builder.isNull(expression);
						default:
							break;
						}
						predicates.add(predicate);
					}
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return predicate;
			}
		};
		return specification;
	}
	public T getById(PK id) {
		
		return baseRepository.findOne(id);
	}

	public void save(T t) {
		
		baseRepository.saveAndFlush(t);
	}

	public void delete(PK id) {
		
		baseRepository.delete(id);
	}
	
}
