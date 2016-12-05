//Cleaned
package org.trinity.yqyl.repository.business.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.trinity.repository.entity.AbstractAuditableEntity;
import org.trinity.yqyl.common.message.lookup.AccountBalanceStatus;
import org.trinity.yqyl.common.message.lookup.AccountCategory;

/**
 * The persistent class for the account_balance database table.
 *
 */
@Entity
@Table(name = "account_balance")
@NamedQuery(name = "AccountBalance.findAll", query = "SELECT a FROM AccountBalance a")
public class AccountBalance extends AbstractAuditableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private AccountCategory category;

    private AccountBalanceStatus status;

    // bi-directional many-to-one association to Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // bi-directional many-to-one association to AccountPosting
    @OneToMany(mappedBy = "accountBalance")
    private List<AccountPosting> accountPostings;

    public AccountBalance() {
    }

    public AccountPosting addAccountPosting(final AccountPosting accountPosting) {
        getAccountPostings().add(accountPosting);
        accountPosting.setAccountBalance(this);

        return accountPosting;
    }

    public Account getAccount() {
        return this.account;
    }

    public List<AccountPosting> getAccountPostings() {
        return this.accountPostings;
    }

    public Double getAmount() {
        return this.amount;
    }

    public AccountCategory getCategory() {
        return this.category;
    }

    public Long getId() {
        return this.id;
    }

    public AccountBalanceStatus getStatus() {
        return this.status;
    }

    public AccountPosting removeAccountPosting(final AccountPosting accountPosting) {
        getAccountPostings().remove(accountPosting);
        accountPosting.setAccountBalance(null);

        return accountPosting;
    }

    public void setAccount(final Account account) {
        this.account = account;
    }

    public void setAccountPostings(final List<AccountPosting> accountPostings) {
        this.accountPostings = accountPostings;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public void setCategory(final AccountCategory category) {
        this.category = category;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setStatus(final AccountBalanceStatus status) {
        this.status = status;
    }

}
