package central_erro.demo.dto.request;

import javax.validation.constraints.NotBlank;

import central_erro.demo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTORequest {
  
  @NotBlank
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private Boolean admin;

  public User buildUser() {
    new User();
    return User.builder().email(this.getEmail()).password(this.getPassword()).admin(this.getAdmin()).build();
  }
}