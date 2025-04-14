package space.app.link.shortener.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.app.link.shortener.server.entity.Link;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByOriginalUrl(String originalUrl);

    Optional<Link> findByShortUrl(String shortUrl);
}
