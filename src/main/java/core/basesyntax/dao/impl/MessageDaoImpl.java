package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save message to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Message receivedMessage = session.get(Message.class, id);
            transaction.commit();
            return receivedMessage;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get message from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            Query<Message> getAllMessageQuery = session.createQuery("from Message", Message.class);
            return getAllMessageQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can' t get all Message from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove message from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
