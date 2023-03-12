package DabuOps.tikkle.member.dto;

import DabuOps.tikkle.global.audit.Auditable;
import DabuOps.tikkle.member.entity.Detail;
import DabuOps.tikkle.member.entity.Member.Gender;
import DabuOps.tikkle.member.entity.Member.MemberState;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {
    @Getter
    @NoArgsConstructor
    public static class Post{
        private String email;
        private String name;

        @Builder
        public Post(String email, String name) {
            this.email = email;
            this.name = name;
        }
    }
    public static class Patch{
        private String name;
        private String location;
        private Integer payDay;
        private Integer initdate;

        @Builder
        public Patch(String name, String location, Integer payDay, Integer initdate) {
            this.name = name;
            this.location = location;
            this.payDay = payDay;
            this.initdate = initdate;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class MeResponse {

        private Long memberId;
        private String email;
        private String name;

        @Builder
        public MeResponse(Long memberId, String email, String name) {
            this.memberId = memberId;
            this.email = email;
            this.name = name;
        }
    }
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class Response extends Auditable {
        private Long memberId;
        private String email;
        private String name;
        private String location;
        private MemberState state;
        private LocalDateTime createAt;
        private LocalDateTime modifiedAt;
        private Gender gender;
        private Integer payDay;
        private Integer initDate;
        @Builder
        public Response(Long memberId, String email, String name, String location,
            MemberState state,
            LocalDateTime createAt, LocalDateTime modifiedAt, Gender gender, Integer payDay,
            Integer initDate) {
            this.memberId = memberId;
            this.email = email;
            this.name = name;
            this.location = location;
            this.state = state;
            this.createAt = createAt;
            this.modifiedAt = modifiedAt;
            this.gender = gender;
            this.payDay = payDay;
            this.initDate = initDate;
        }
    }

}
