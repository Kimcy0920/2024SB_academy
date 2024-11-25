package edu.du.sb1101.point;

import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointLogService {

    private final MemberRepository memberRepository;
    private final PointLogRepository pointLogRepository;

    public void addPoints(Member member, int points, String description) {
        member.setPoint(member.getPoint() + points);
        memberRepository.save(member);

        // 포인트 로그 기록
        PointLog log = new PointLog();
        log.setMember(member);
        log.setActionType("EARNED");
        log.setPoints(points);
        log.setDescription(description);
        pointLogRepository.save(log);
    }

    public void usePoints(Member member, int points, String description) {
        if (member.getPoint() < points) {
            throw new IllegalArgumentException("포인트가 부족합니다.");
        }

        member.setPoint(member.getPoint() - points);
        memberRepository.save(member);

        // 포인트 로그 기록
        PointLog log = new PointLog();
        log.setMember(member);
        log.setActionType("USED");
        log.setPoints(-points);
        log.setDescription(description);
        pointLogRepository.save(log);
    }
}
