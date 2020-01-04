package com.hc.listing.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hc.listing.models.VehicleEntity;

@Service
public class VehicleSearchRepositoryImpl implements VehicleSearchRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<VehicleEntity> filterByMakeModelColorAndYear(List<String> make, List<String> model, List<String> color,
			List<Integer> year) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<VehicleEntity> cq = cb.createQuery(VehicleEntity.class);
		Root<VehicleEntity> root = cq.from(VehicleEntity.class);
		List<Predicate> predicates = new ArrayList<>();

		if (!CollectionUtils.isEmpty(make)) {
			Expression<String> parentExpression = root.get("make");
			predicates.add(parentExpression.in(make));
		}
		if (!CollectionUtils.isEmpty(model)) {
			Expression<String> parentExpression = root.get("model");
			predicates.add(parentExpression.in(model));
		}
		if (!CollectionUtils.isEmpty(color)) {
			Expression<String> parentExpression = root.get("color");
			predicates.add(parentExpression.in(color));
		}
		if (!CollectionUtils.isEmpty(year)) {
			Expression<String> parentExpression = root.get("year");
			predicates.add(parentExpression.in(year));
		}
		cq.where(predicates.toArray(new Predicate[0]));
		return em.createQuery(cq).getResultList();
	}
}
