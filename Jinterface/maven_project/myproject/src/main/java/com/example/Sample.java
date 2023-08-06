package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;

public class Sample {
  Evaluator evaluator;

  public Sample() throws Exception {
    String modelFolder = "/Users/shubham.kumar/Downloads/";
    String modelName = "storable_to_pps_queue_model.pmml";
    Path modelPath = Paths.get(modelFolder, modelName);
    evaluator = new LoadingModelEvaluatorBuilder()
        .load(modelPath.toFile())
        .build();
    evaluator.verify();
  }

  public Double evaluate(List<Double> inputValues) throws Exception {

    List<InputField> inputFields = evaluator.getInputFields();

    Map<String, Double> features = new HashMap<>();
    List<String> keys = new ArrayList<String>();
    keys.add("idc_time");
    keys.add("x_start");
    keys.add("y_start");
    keys.add("x_goal");
    keys.add("y_goal");

    for (int i = 0; i < 5; i++) {
      Double val  = inputValues.get(i);
      String key = keys.get(i);
      features.put(key, val);
    }

    Map<String, FieldValue> arguments = new LinkedHashMap<>();
    for (InputField inputField : inputFields) {
      String inputName = inputField.getName();
      Double value = features.get(inputName.toString());
      FieldValue inputValue = inputField.prepare(value);
      arguments.put(inputName, inputValue);
    }

    Map<String, ?> results = evaluator.evaluate(arguments);
    Map<String, ?> resultRecord = EvaluatorUtil.decodeAll(results);
    return (Double)resultRecord.get("y");
  }
}
