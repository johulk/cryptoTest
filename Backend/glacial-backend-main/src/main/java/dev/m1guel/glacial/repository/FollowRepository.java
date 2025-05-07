package dev.m1guel.glacial.repository;

import dev.m1guel.glacial.model.Follow;
import dev.m1guel.glacial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {

    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findAllByFollowing(User user);
    List<Follow> findAllByFollower(User user);

}
