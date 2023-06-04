package urna;

//#if Federal || Municipal
public class President extends Candidate {

      public President(Builder builder) {
          super(builder);
      }

      public static class Builder extends Candidate.Builder<Builder> {

          @Override
          public Builder getThis() {
              return this;
          }
          public President build() {
              return new President(this);
          }

      }

  }
// #endif
