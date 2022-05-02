package ru.hh.superscoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.hh.superscoring.dao.QuestionDao;
import ru.hh.superscoring.dto.QuestionDto;
import ru.hh.superscoring.entity.Question;

public class QuestionService {
  private final QuestionDao questionDao;
  private final ObjectMapper mapper = new ObjectMapper();


  public QuestionService(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  public List<Integer> getQuestionsForStart(Integer testId, Integer testSize) {
    List<Integer> allQuestionsId = questionDao.getQuestionsForTest(testId);
    Collections.shuffle(allQuestionsId);
    return allQuestionsId.subList(0, testSize);
  }

  public QuestionDto questionDtoFromEntity(Question q) throws JsonProcessingException {
    return new QuestionDto(q.getWording(), mapper.readValue(q.getPayload(), JsonNode.class));
  }

  public HashMap<String, Map<Integer, QuestionDto>> getQuestionMapDto(List<Integer> ids) throws JsonProcessingException {
    HashMap<Integer, QuestionDto> res = new HashMap<>();
    for (Integer id : ids) {
      res.put(ids.indexOf(id) + 1, questionDtoFromEntity(questionDao.get(Question.class, id)));
    }
    HashMap<String, Map<Integer, QuestionDto>> dto = new HashMap<>();
    dto.put("question", res);
    return dto;
  }

}
