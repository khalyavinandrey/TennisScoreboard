package Project3.services;

import Project3.models.FinishedMatch;
import Project3.models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FinishedMatchesService {

    private final SessionFactory sessionFactory;
    private final PlayersService playersService;

    @Autowired
    public FinishedMatchesService(SessionFactory sessionFactory, PlayersService playersService) {
        this.sessionFactory = sessionFactory;
        this.playersService = playersService;
    }

    public List<FinishedMatch> getAllFinishedMatches() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM FinishedMatch").getResultList();
    }

    public FinishedMatch getMatchByPlayer(int id) {
        Session session = sessionFactory.getCurrentSession();
        return (FinishedMatch) session.createQuery("SELECT m FROM FinishedMatch m WHERE m.firstPlayer.id = ?1 OR" +
                " m.secondPlayer.id = ?1 OR m.winner.id = ?1").setParameter(1, id).getSingleResult();

    }

    public List<FinishedMatch> getFinishedMatchesWithPagination(Integer page) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM FinishedMatch");
        int firstResult = page * 3;
        query.setFirstResult(firstResult);
        query.setMaxResults(3);
        return query.list();
    }

}
