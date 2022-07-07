<#import "parts/common.ftlh" as c>
<#import "parts/script.ftlh" as s>

<@c.page>
<div class="container"></div>
<form class="form-horizontal" id="test_form" method="post" action="add" enctype="multipart/form-data">
    <fieldset>
        <div class="row">

            <!-- Form Name -->
            <legend>Создать новый тест</legend>

            <!-- Название теста-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="testName">Название теста</label>
                <div class="col-md-4">
                    <input id="testName" name="testName" type="text" placeholder="" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Описание теста-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="description">Описание</label>
                <div class="col-md-4">
                    <input id="description" name="description" type="text" placeholder="" class="form-control input-md" required="">

                </div>
            </div>

            <!-- Количество вопросов в прохождении-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="numberOfQuestionsInAttempt">Количество вопросов в прохождении</label>
                <div class="col-md-4">
                    <input id="numberOfQuestionsInAttempt" name="numberOfQuestionsInAttempt" value="10" type="number" placeholder="" class="form-control input-md" required="">
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="numberOfAttempts">Количество прохождений в цикле</label>
                <div class="col-md-4">
                    <input id="numberOfAttempts" name="numberOfAttempts" required="" value="3" type="number" placeholder="" class="form-control input-md">
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="timeBetweenCycles">Дней между циклами прохождений</label>
                <div class="col-md-4">
                    <input id="timeBetweenCycles" name="timeBetweenCycles" required="" value="20" type="number" placeholder="" class="form-control input-md">
                </div>
            </div>

            <!-- Время на прохождение-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="timeLimit">Время на прохождение</label>
                <div class="col-md-4">
                    <input id="timeLimit" name="timeLimit" type="number" value="300" placeholder="" class="form-control input-md" required="">
                </div>
            </div>

            <!-- Hidden question counter-->
            <div class="form-group">
                <input id="questionCounter" name="questionCounter" type="hidden" class="form-control input-md" value="1">
            </div>
        </div>

        <!-- Блок вопросов -->

        <div class="row" name="questions" id="questions">
            <div class="questionBlock col-12 col-sm-6 col-md-4 col-xl-3" name="questionBlock" id="questionBlock1">
                <!-- Text input-->
                <div class="form-group">
                    <label class="control-label" for="question1">Вопрос 1</label>
                    <div class="">
                        <input id="question1" name="question1" type="text" placeholder="" class="form-control input-md" required="">
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="control-label" for="weight1">Вес</label>
                    <div class="">
                        <input id="weight1" name="weight1" type="number" value="1" class="form-control input-md" required="">

                    </div>
                </div>

                <!-- File Button -->
                <div class="form-group">
                    <label class=" control-label" for="file1">Добавить медиафайл</label>
                    <div class="">
                        <input id="file1" name="file1" class="input-file" type="file">
                    </div>
                </div>

                <!-- Блок подответов -->

                <div name="subAnswerBlock1" id="subAnswerBlock1">


                    <!-- Подответ -->
                    <div name="subAnswer1_1" id="subAnswer1_1">

                        <!-- Счётчик подответов -->
                        <div class="form-group">
                            <input id="subAnswerCounter1" name="subAnswerCounter1" type="hidden" class="form-control input-md" value="1">
                        </div>

                        <!-- Блок вариантов ответов -->
                        <div name="answerOptions1_1" id="answerOptions1_1">

                            <!-- Счётчик вариантов-->
                            <div class="form-group">
                                <input id="optionCounter1_1" name="optionCounter1_1" type="hidden" class="form-control input-md" value="2">
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <label class="control-label" for="answerOption1_1_1">Вариант 1</label>
                                <div class="">
                                    <input id="answerOption1_1_1" name="answerOption1_1_1" type="text" placeholder="" class="form-control input-md" required="">
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <label class="control-label" for="answerOption1_1_2">Вариант 2</label>
                                <div class="">
                                    <input id="answerOption1_1_2" name="answerOption1_1_2" type="text" placeholder="" class="form-control input-md" required="">
                                </div>
                            </div>
                        </div>
                        <!-- Конец блока вариантов ответов -->

                        <!-- Button -->
                        <div class="form-group">
                            <label class="control-label" for="addAnswerOption1"></label>
                            <div class="">
                                <button id="addAnswerOption1_1" name="addAnswerOption1_1" class="btn btn-primary" type="button" onclick="add_answer_option(1,1)">+</button>
                            </div>
                        </div>
                    </div>
                    <!-- Конец подответа -->

                </div>
                <!--  Конец блока подответов-->

                <!-- Кнопка добавления подответа -->
                <div class="form-group">
                    <label class="control-label" for="addSubAnswer1"></label>
                    <div class="">
                        <button id="addSubAnswer1" name="addSubAnswer1" class="btn btn-primary"  type="button" disabled onclick="add_sub_answer(1)">Добавить подответ</button>
                    </div>
                </div>

                <!-- Multiple Radios -->
                <div class="form-group">
                    <label class="control-label" for="answerType1">Тип ответа</label>
                    <div class="">
                        <div class="radio">
                            <label for="answerType1-0">
                                <input type="radio" name="answerType1" id="answerType1-0" value="SINGLE_CHOICE" checked="checked" onclick="EnableDisabledButton(1)">
                                SINGLE_CHOICE
                            </label>
                        </div>
                        <div class="radio">
                            <label for="answerType1-1">
                                <input type="radio" name="answerType1" id="answerType1-1" value="MULTIPLE_CHOICE" onclick="EnableDisabledButton(1)">
                                MULTIPLE_CHOICE
                            </label>
                        </div>
                        <div class="radio">
                            <label for="answerType1-2">
                                <input type="radio" name="answerType1" id="answerType1-2" value="MULTIPLE_QUESTIONS_SINGLE_CHOICE" onclick="EnableDisabledButton(1)">
                                MULTIPLE_QUESTIONS_SINGLE_CHOICE
                            </label>
                        </div>
                        <div class="radio">
                            <label for="answerType1-3">
                                <input type="radio" name="answerType1" id="answerType1-3" value="RANKING" onclick="EnableDisabledButton(1)">
                                RANKING
                            </label>
                        </div>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="control-label" for="answer1">Ответ</label>
                    <div class="">
                        <input id="answer1" name="answer1" type="text" placeholder="" class="form-control input-md" data-toggle="tooltip" required="">
                        <span class="help-block">SINGLE_CHOICE: 1<br> MULTIPLE_CHOICE: 1,2<br> MULTIPLE_QUESTIONS_SINGLE_CHOICE: 1:2, 2:1<br> RANKING: 2,1</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- Конец блока вопросов -->

        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="addQuestion"></label>
            <div class="">
                <button id="addQuestion" type="button" name="addQuestion" class="btn btn-primary" onclick="add_question()">Добавить вопрос</button>
            </div>
        </div>

    </fieldset>
</form>
<!-- Button Submit-->
<div class="form-group">
    <label class="col-md-4 control-label" for="singlebutton"></label>
    <div class="col-md-4">
        <button class="btn btn-primary" type="submit" form="test_form" value="Submit">Создать тест</button>
    </div>
</div>
</div>
<@s.addScript />
</@c.page>

