package edu.du.sb1101.registerMember.service;

import edu.du.sb1101.point.PointLog;
import edu.du.sb1101.point.PointLogRepository;
import edu.du.sb1101.registerMember.entity.Member;
import edu.du.sb1101.registerMember.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointLogRepository pointLogRepository;

    @Transactional
    public void addPoints(String username, int points, String description) {
        // 회원 정보 조회
        Member member = memberRepository.findByUsername(username);

        if (member != null) {
            // 포인트 추가
            int newPoint = member.getPoint() + points;
            member.setPoint(newPoint);
            memberRepository.save(member);  // 포인트 업데이트

            // 포인트 로그 기록
            PointLog pointLog = PointLog.builder()
                    .member(member)
                    .createdAt(LocalDateTime.now())
                    .actionType("EARNED")  // 포인트 적립 타입
                    .points(points)
                    .description(description)
                    .build();

            pointLogRepository.save(pointLog);  // 포인트 로그 저장
        }
    }
}