package central_erro.demo.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import central_erro.demo.entities.Log;

public interface LogInterface extends GenericInterface<Log>{
  @Override
  List<Log> findAll();

  List<?> findAllLogsByParam(Map<String, Object> params);

  Optional<Log> findById(Long id);

  Boolean deleteById(Long id);

}