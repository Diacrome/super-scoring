<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Super scoring</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <nav class="navbar navbar-expand-lg bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Super Scoring</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="dashboard">Home</a>
                    </li>
                </ul>
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="new-test">Создать тест</a>
                    </li>
                </ul>
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="tests">Управление тестами</a>
                    </li>
                </ul>
                <form class="d-flex" role="search">
                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>
    <script src="script.js"></script>
    <style type="text/css">
      body{
        padding-left: 20px;
      }
      .row {
        padding-top:  10px;
        padding-bottom: 10px;
        margin-top: 5px;

        border-style: solid;
      }
    </style>
</head>
<body>
<form class="form-horizontal" id="test_form" method="post" action="add-test">

    <!-- Form Name -->
    <legend></legend>

    <!-- Text input-->
    <div class="form-group">
        <label class="col-md-4 control-label" for="textinput">Название теста</label>
        <div class="col-md-4">
            <input id="textinput" name="test-name" type="text" placeholder="" class="form-control input-md">
        </div>
    </div>

    <!-- Text input-->
    <div class="form-group">
        <label class="col-md-4 control-label" for="textinput">Описание</label>
        <div class="col-md-4">
            <input id="description" name="description" type="text" placeholder="" class="form-control input-md">
        </div>
    </div>

    <div class="row" id="row1">
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="textinput">Вопрос 1</label>
            <div class="col-md-4">
                <input id="textinput" name="question1" type="text" placeholder="" class="form-control input-md">
            </div>
        </div>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="textinput">Вес</label>
            <div class="col-md-1">
                <input id="textinput" name="weight1" type="text" placeholder="1" class="form-control input-md">
            </div>
        </div>

        <!-- File Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="filebutton">Добавить изображение</label>
            <div class="col-md-4">
                <input id="filebutton" name="filebutton1" class="input-file" type="file">
            </div>
        </div>

        <!-- Appended checkbox -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант 1</label>
            <div class="col-md-4">
                <div class="input-group">
                    <input id="appendedcheckbox" name="answer1_1" class="form-control" type="text" placeholder="">
                    <span class="input-group-addon">
                  <input type="checkbox" name="checkbox1_1">
                </span>
                </div>

            </div>
        </div>

        <!-- Appended checkbox -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант 2</label>
            <div class="col-md-4">
                <div class="input-group">
                    <input id="appendedcheckbox" name="answer2_1" class="form-control" type="text" placeholder="">
                    <span class="input-group-addon">
                  <input type="checkbox" name="checkbox2_1">
                </span>
                </div>

            </div>
        </div>

        <!-- Appended checkbox -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант 3</label>
            <div class="col-md-4">
                <div class="input-group">
                    <input id="appendedcheckbox" name="answer3_1" class="form-control" type="text" placeholder="">
                    <span class="input-group-addon">
                  <input type="checkbox" name="checkbox3_1">
                </span>
                </div>

            </div>
        </div>

        <!-- Appended checkbox -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант4</label>
            <div class="col-md-4">
                <div class="input-group">
                    <input id="appendedcheckbox" name="answer4_1" class="form-control" type="text" placeholder="">
                    <span class="input-group-addon">
                  <input type="checkbox" name="checkbox4_1">
                </span>
                </div>

            </div>
        </div>

        <!-- Counter -->
        <div class="form-group">
            <div class="col-md-4">
                <input type="hidden" id="counter" name="counter">
            </div>
        </div>

    </div>
</form>

<!-- Button -->
<div class="form-group">
    <label class="col-md-4 control-label" for="singlebutton"></label>
    <div class="col-md-4">
        <button id="singlebutton" name="singlebutton" class="btn btn-primary" onclick="add_question()">Добавить вопрос</button>
    </div>
</div>

<!-- Button -->
<div class="form-group">
    <label class="col-md-4 control-label" for="singlebutton"></label>
    <div class="col-md-4">
        <button class="btn btn-primary" type="submit" form="test_form" value="Submit">Создать тест</button>
    </div>
</div>

<script>
    var counter=1;
function add_question(){
	counter++;
	document.getElementById("counter").value = counter;
	var html = '<div class="row" id="row1">\
          <!-- Text input-->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="textinput">Вопрос '+counter+'</label>\
            <div class="col-md-4">\
              <input id="textinput" name="question'+counter+'" type="text" placeholder="" class="form-control input-md">\
            </div>\
          </div>\
          <!-- Text input-->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="textinput">Вес</label>\
            <div class="col-md-1">\
              <input id="textinput" name="weight'+counter+'" type="text" placeholder="1" class="form-control input-md">\
            </div>\
          </div>\
          <!-- File Button -->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="filebutton">Добавить изображение</label>\
            <div class="col-md-4">\
              <input id="filebutton" name="filebutton'+counter+'" class="input-file" type="file">\
            </div>\
          </div>\
          <!-- Appended checkbox -->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант 1</label>\
            <div class="col-md-4">\
              <div class="input-group">\
                <input id="appendedcheckbox" name="answer1_'+counter+'" class="form-control" type="text" placeholder="">\
                <span class="input-group-addon">\
                  <input type="checkbox" name="checkbox1_'+counter+'">\
                </span>\
              </div>\
            </div>\
          </div>\
          <!-- Appended checkbox -->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант 2</label>\
            <div class="col-md-4">\
              <div class="input-group">\
                <input id="appendedcheckbox" name="answer2_'+counter+'" class="form-control" type="text" placeholder="">\
                <span class="input-group-addon">\
                  <input type="checkbox" name="checkbox2_'+counter+'">\
                </span>\
              </div>\
            </div>\
          </div>\
          <!-- Appended checkbox -->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант 3</label>\
            <div class="col-md-4">\
              <div class="input-group">\
                <input id="appendedcheckbox" name="answer3_'+counter+'" class="form-control" type="text" placeholder="">\
                <span class="input-group-addon">\
                  <input type="checkbox" name="checkbox3_'+counter+'">\
                </span>\
              </div>\
            </div>\
          </div>\
          <!-- Appended checkbox -->\
          <div class="form-group">\
            <label class="col-md-4 control-label" for="appendedcheckbox">Вариант4</label>\
            <div class="col-md-4">\
              <div class="input-group">\
                <input id="appendedcheckbox" name="answer4_'+counter+'" class="form-control" type="text" placeholder="">\
                <span class="input-group-addon">\
                  <input type="checkbox" name="checkbox4_'+counter+'">\
                </span>\
              </div>\
            </div>\
          </div>\
        </div>';


	var form = document.getElementById('test_form');
	form.innerHTML += html;
}

</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
