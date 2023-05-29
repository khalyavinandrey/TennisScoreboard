package Project3.services;

import Project3.dao.MatchDAO;
import Project3.models.OngoingMatch;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SavingFinishedMatchesService {

    private final SessionFactory sessionFactory;
    private final MatchDAO matchDAO;

    public SavingFinishedMatchesService(SessionFactory sessionFactory, MatchDAO matchDAO) {
        this.sessionFactory = sessionFactory;
        this.matchDAO = matchDAO;
    }

    public void createFinishedMatch(OngoingMatch ongoingMatch) {
        matchDAO.createFinishedMatch(ongoingMatch);
    }

}
