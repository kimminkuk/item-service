package hello.itemservice.domain.Member;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Member {
    private Long memberId;
    private String memberName;

    public Member() {
    }

    public Member(String memberName) {
        this.memberName = memberName;
    }
}
