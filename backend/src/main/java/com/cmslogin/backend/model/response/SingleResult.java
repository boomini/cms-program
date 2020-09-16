package com.cmslogin.backend.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
  // 결과가 단일건인 api를 담는 모델
  // Generic Interface 에 <T>를 지정하여 어떤 형태의 값도 넣을 수 있도록 구현하였다.
  // 또한 CommonResult를 상속받으므로 API요청 결과도 같이 출력됩니다.
  private T data;

}
