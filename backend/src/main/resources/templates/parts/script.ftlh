<#macro addScript>
<script>
     var questionCounter = 1;
function add_question() {
	questionCounter++;
	document.getElementById("questionCounter").value = questionCounter;

	let newQuestion = document.createElement('div');
	newQuestion.setAttribute("class", "questionBlock col-12 col-sm-6 col-md-4 col-xl-3");
	newQuestion.setAttribute("name", "questionBlock" + questionCounter);
	newQuestion.setAttribute("id", "questionBlock" + questionCounter);
	newQuestion.innerHTML = `
                    <!-- Text input-->
                    <div class="form-group">
                        <label class="control-label" for="question`+questionCounter+`">Вопрос `+questionCounter+`</label>
                        <div class="">
                            <input id="question`+questionCounter+`" name="question`+questionCounter+`" type="text" placeholder="" class="form-control input-md" required="">
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="control-label" for="weight`+questionCounter+`">Вес</label>
                        <div class="">
                            <input id="weight`+questionCounter+`" name="weight`+questionCounter+`" type="number" value="1" class="form-control input-md" required="">

                        </div>
                    </div>

                    <!-- File Button -->
                    <div class="form-group">
                        <label class=" control-label" for="file`+questionCounter+`">Добавить медиафайл</label>
                        <div class="">
                            <input id="file`+questionCounter+`" name="file`+questionCounter+`" class="input-file" type="file">
                        </div>
                    </div>

                    <!-- Блок подответов -->

                    <div name="subAnswerBlock`+questionCounter+`" id="subAnswerBlock`+questionCounter+`">


                        <!-- Подответ -->
                        <div name="subAnswer`+questionCounter+`_1" id="subAnswer`+questionCounter+`_1">

                            <!-- Счётчик подответов -->
                            <div class="form-group">
                                <input id="subAnswerCounter`+questionCounter+`" name="subAnswerCounter`+questionCounter+`" type="hidden" class="form-control input-md" value="1">
                            </div>

                            <!-- Блок вариантов ответов -->
                            <div name="answerOptions`+questionCounter+`_1" id="answerOptions`+questionCounter+`_1">

                                <!-- Счётчик вариантов-->
                                <div class="form-group">
                                    <input id="optionCounter`+questionCounter+`_1" name="optionCounter`+questionCounter+`_1" type="hidden" class="form-control input-md" value="2">
                                </div>

                                <!-- Text input-->
                                <div class="form-group">
                                    <label class="control-label" for="answerOption`+questionCounter+`_1_1">Вариант 1</label>
                                    <div class="">
                                        <input id="answerOption`+questionCounter+`_1_1" name="answerOption`+questionCounter+`_1_1" type="text" placeholder="" class="form-control input-md" required="">
                                    </div>
                                </div>

                                <!-- Text input-->
                                <div class="form-group">
                                    <label class="control-label" for="answerOption`+questionCounter+`_1_2">Вариант 2</label>
                                    <div class="">
                                        <input id="answerOption`+questionCounter+`_1_2" name="answerOption`+questionCounter+`_1_2" type="text" placeholder="" class="form-control input-md" required="">
                                    </div>
                                </div>
                            </div>
                            <!-- Конец блока вариантов ответов -->

                            <!-- Button -->
                            <div class="form-group">
                                <label class="control-label" for="addAnswerOption`+questionCounter+`"></label>
                                <div class="">
                                    <button id="addAnswerOption`+questionCounter+`_1" name="addAnswerOption`+questionCounter+`_1" class="btn btn-primary" type="button" onclick="add_answer_option(`+questionCounter+`,1)">+</button>
                                </div>
                            </div>
                        </div>
                        <!-- Конец подответа -->

                    </div>
                    <!--  Конец блока подответов-->

                    <!-- Кнопка добавления подответа -->
                    <div class="form-group">
                        <label class="control-label" for="addSubAnswer`+questionCounter+`"></label>
                        <div class="">
                            <button id="addSubAnswer`+questionCounter+`" name="addSubAnswer`+questionCounter+`" class="btn btn-primary"  type="button" disabled onclick="add_sub_answer(`+questionCounter+`)">Добавить подответ</button>
                        </div>
                    </div>

                    <!-- Multiple Radios -->
                    <div class="form-group">
                        <label class="control-label" for="answerType`+questionCounter+`">Тип ответа</label>
                        <div class="">
                            <div class="radio">
                                <label for="answerType`+questionCounter+`-0">
                                    <input type="radio" name="answerType`+questionCounter+`" id="answerType`+questionCounter+`-0" value="SINGLE_CHOICE" checked="checked" onclick="EnableDisabledButton(`+questionCounter+`)">
                                    SINGLE_CHOICE
                                </label>
                            </div>
                            <div class="radio">
                                <label for="answerType`+questionCounter+`-1">
                                    <input type="radio" name="answerType`+questionCounter+`" id="answerType`+questionCounter+`-1" value="MULTIPLE_CHOICE" onclick="EnableDisabledButton(`+questionCounter+`)">
                                    MULTIPLE_CHOICE
                                </label>
                            </div>
                            <div class="radio">
                                <label for="answerType`+questionCounter+`-2">
                                    <input type="radio" name="answerType`+questionCounter+`" id="answerType`+questionCounter+`-2" value="MULTIPLE_QUESTIONS_SINGLE_CHOICE" onclick="EnableDisabledButton(`+questionCounter+`)">
                                    MULTIPLE_QUESTIONS_SINGLE_CHOICE
                                </label>
                            </div>
                            <div class="radio">
                                <label for="answerType`+questionCounter+`-3">
                                    <input type="radio" name="answerType`+questionCounter+`" id="answerType`+questionCounter+`-3" value="RANKING" onclick="EnableDisabledButton(`+questionCounter+`)">
                                    RANKING
                                </label>
                            </div>
                        </div>
                    </div>

                    <!-- Text input-->
                    <div class="form-group">
                        <label class="control-label" for="answer`+questionCounter+`">Ответ</label>
                        <div class="">
                            <input id="answer`+questionCounter+`" name="answer`+questionCounter+`" type="text" placeholder="" class="form-control input-md" data-toggle="tooltip" required="">
                            <span class="help-block">SINGLE_CHOICE: 1<br> MULTIPLE_CHOICE: 1,2<br> MULTIPLE_QUESTIONS_SINGLE_CHOICE: 1:2, 2:1<br> RANKING: 2,1</span>
                        </div>
                    </div>
	`;

	document.getElementById("questions").appendChild(newQuestion);
}

function add_answer_option(question, subAnswer){
	let optionCounter = document.getElementById("optionCounter"+question + "_" + subAnswer).value;
	document.getElementById("optionCounter"+question + "_" + subAnswer).value = ++optionCounter;

	let newOption = document.createElement('div');
	newOption.setAttribute("class", "form-group");
	newOption.innerHTML = `
	<label class="control-label" for="answerOption`+question+`_`+subAnswer+`_`+optionCounter+`">Вариант `+optionCounter+`</label>
	<div class="">
	<input id="answerOption`+question+`_`+subAnswer+`_`+optionCounter+`" name="answerOption`+question+`_`+subAnswer+`_`+optionCounter+`" type="text" placeholder="" class="form-control input-md" required="">
	</div>

	`;
	document.getElementById("answerOptions"+question + "_" + subAnswer).appendChild(newOption);
}

function add_sub_answer(question){
	let subAnswerCounter = document.getElementById("subAnswerCounter"+question).value;
	document.getElementById("subAnswerCounter"+question).value = ++subAnswerCounter;

	let newSubAnswer = document.createElement('div');
	newSubAnswer.setAttribute("name", "subAnswer" + question + "_" + subAnswerCounter);
	newSubAnswer.setAttribute("id", "subAnswer" + question + "_" + subAnswerCounter);
	newSubAnswer.innerHTML = `
    <!-- Счётчик подответов -->
    <div class="form-group">
        <input id="subAnswerCounter`+question+`" name="subAnswerCounter`+question+`" type="hidden" class="form-control input-md" value="1">
    </div>

    <!-- Блок вариантов ответов -->
    <div name="answerOptions`+question+`_`+subAnswerCounter+`" id="answerOptions`+question+`_`+subAnswerCounter+`">

        <!-- Счётчик вариантов-->
        <div class="form-group">
            <input id="optionCounter`+question+`_`+subAnswerCounter+`" name="optionCounter`+question+`_`+subAnswerCounter+`" type="hidden" class="form-control input-md" value="2">
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="control-label" for="answerOption`+question+`_`+subAnswerCounter+`_1">Вариант 1</label>
            <div class="">
                <input id="answerOption`+question+`_`+subAnswerCounter+`_1" name="answerOption`+question+`_`+subAnswerCounter+`_1" type="text" placeholder="" class="form-control input-md" required="">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="control-label" for="answerOption`+question+`_`+subAnswerCounter+`_2">Вариант 2</label>
            <div class="">
                <input id="answerOption`+question+`_`+subAnswerCounter+`_2" name="answerOption`+question+`_`+subAnswerCounter+`_2" type="text" placeholder="" class="form-control input-md" required="">
            </div>
        </div>
    </div>
    <!-- Конец блока вариантов ответов -->

    <!-- Button -->
    <div class="form-group">
        <label class="control-label" for="addAnswerOption`+question+`_`+subAnswerCounter+`"></label>
        <div class="">
            <button id="addAnswerOption`+question+`_`+subAnswerCounter+`" name="addAnswerOption`+question+`_`+subAnswerCounter+`" class="btn btn-primary" type="button" onclick="add_answer_option(`+question+`,`+subAnswerCounter+`)">+</button>
        </div>
    </div>
	`;
	document.getElementById("subAnswerBlock"+question).appendChild(newSubAnswer);
}

function EnableDisabledButton(question) {
        var radio3 = document.getElementById("answerType"+question+"-2");
        var buttonSubAnswer = document.getElementById("addSubAnswer" + question);
        buttonSubAnswer.disabled = radio3.checked ? false : true;
        if (!buttonSubAnswer.disabled) {
            buttonSubAnswer.focus();
        }
    }

</script>
</#macro>
