package urna;
import java.util.Set;

public class UniversityCandidate extends Candidate {

    public UniversityCandidate(Builder builder) {
        super(builder);
    }

    public static class Builder extends Candidate.Builder<Builder> {

        @Override
        public Builder getThis() {
            return this;
        }
        public UniversityCandidate build() {
            return new UniversityCandidate(this);
        }

    }

}