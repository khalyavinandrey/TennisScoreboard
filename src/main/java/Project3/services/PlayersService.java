package Project3.services;

import Project3.models.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class PlayersService {
    private final SessionFactory sessionFactory;

    @Autowired
    public PlayersService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Player> getPlayers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p from Player p").getResultList();
    }

    public boolean isPersonUnique(String name) {
        for (Player player : getPlayers()) {
            if (player.getName().equals(name)) return false;
        }
        return true;
    }

    @Transactional
    public Player createPlayer(String name) {
        Session session = sessionFactory.getCurrentSession();
        Player player = new Player();
        player.setName(name);
        session.persist(player);
        return player;
    }

    public Player getPlayerByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Player player = null;
        try {
            player = (Player) session.createQuery("SELECT p FROM Player p WHERE p.name = ?1").setParameter(1, name).getSingleResult();
        } catch (NoResultException e){
            player = null;
        }
        return player;
    }
}
