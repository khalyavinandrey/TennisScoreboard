package Project3.services;

import Project3.models.OngoingMatch;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class OngoingMatchesService {
    private final SessionFactory sessionFactory;

    @Autowired
    public OngoingMatchesService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<OngoingMatch> getMatches() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT m FROM OngoingMatch m").getResultList();
    }

    public OngoingMatch getMatchById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        OngoingMatch ongoingMatch = session.get(OngoingMatch.class, id);
        Hibernate.initialize(ongoingMatch.getFirstPlayer());
        Hibernate.initialize(ongoingMatch.getSecondPlayer());
        return ongoingMatch;
    }

    @Transactional
    public void createMatch(OngoingMatch ongoingMatch) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ongoingMatch);
    }

    @Transactional
    public void update(int id, OngoingMatch updatedOngoingMatch, String chosenPlayer) {
        Session session = sessionFactory.getCurrentSession();
        OngoingMatch ongoingMatch = session.get(OngoingMatch.class, id);
        if (chosenPlayer.equals("first")) {
            ongoingMatch.setFirstPlayerScore(updatedOngoingMatch.getFirstPlayerScore());
        } else if (chosenPlayer.equals("second")) {
            ongoingMatch.setSecondPlayerScore(updatedOngoingMatch.getSecondPlayerScore());
        }
    }

    @Transactional
    public void setWinner(int id, OngoingMatch updatedOngoingMatch) {
        Session session = sessionFactory.getCurrentSession();
        OngoingMatch ongoingMatch = session.get(OngoingMatch.class, id);
        ongoingMatch.setWinner(updatedOngoingMatch.getWinner());
    }

}
