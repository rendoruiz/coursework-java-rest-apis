package dmit2015.repository;

import dmit2015.entity.Bill;
import dmit2015.security.BillSecurityInterceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@Interceptors({BillSecurityInterceptor.class})
public class BillRepository {

    @Inject
    private SecurityContext _securityContext;

    @PersistenceContext(unitName = "h2database-jpa-pu")
    private EntityManager _entityManager;

    public void create(Bill newBill)
    {
        if (_securityContext.getCallerPrincipal() == null) {
            throw new RuntimeException("Access denied. You must login before you can perform this operation");
        }
        String username = _securityContext.getCallerPrincipal().getName();
        newBill.setUsername(username);

        newBill.setAmountBalance(newBill.getAmountDue());
        _entityManager.persist(newBill);
    }

    private void remove(Bill existingBill) {
        // Delete all the BillPayment associated with the existingBill
        _entityManager.createQuery("DELETE FROM BillPayment bp WHERE bp.billToPay.id = :billIdValue")
                .setParameter("billIdValue", existingBill.getId())
                .executeUpdate();
        // Delete the existingBill
        _entityManager.remove(existingBill);
    }

    public void delete(Long billId) {
        Optional<Bill> optionalBill = findOneById(billId);
        if (optionalBill.isPresent()) {
            Bill existingBill = optionalBill.get();
            remove(existingBill);
        }
    }

    public List<Bill> findAll() {
        List<Bill> queryResultList;
        if (_securityContext.isCallerInRole("ADMIN")) {
            queryResultList = _entityManager.createQuery(
                    "SELECT b FROM Bill b ORDER BY b.dueDate DESC",
                    Bill.class)
                    .getResultList();
        } else if (_securityContext.isCallerInRole("REGISTER_USER")) {
            String username = _securityContext.getCallerPrincipal().getName();
            queryResultList = _entityManager.createQuery(
                    "SELECT b FROM Bill b WHERE b.username = :usernameValue ORDER BY b.dueDate DESC",
                    Bill.class)
                    .setParameter("usernameValue", username)
                    .getResultList();
        } else {
            queryResultList = null;
        }
        return queryResultList;
    }

    public Optional<Bill> findOneById(Long billId) {
        Optional<Bill> optionalSingleResult = Optional.empty();
        Bill querySingleResult = _entityManager.find(Bill.class, billId);
        if (querySingleResult != null) {
            optionalSingleResult = Optional.of(querySingleResult);
        }
        return optionalSingleResult;
    }

    public void update(Bill existingBill) {
        Optional<Bill> optionalBill = findOneById(existingBill.getId());
        if (optionalBill.isPresent()) {
            Bill editBill = optionalBill.get();
            editBill.setPayeeName(existingBill.getPayeeName());
            editBill.setDueDate(existingBill.getDueDate());
            editBill.setAmountDue(existingBill.getAmountDue());
            editBill.setAmountBalance(existingBill.getAmountBalance());
            editBill.setVersion(existingBill.getVersion());
            _entityManager.merge(editBill);
            _entityManager.flush();
        }
    }
}