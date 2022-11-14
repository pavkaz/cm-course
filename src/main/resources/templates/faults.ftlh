<!doctype html>
<html>

<head>
  <title>cm-course</title>
</head>

<body>
<style>
    input[type="text"] {
        width:  700px;
        height: 15px;
        padding: 5px 10px 5px 10px;
        border:1px solid #999;
        font-size:12px;
        font-family: Consolas, monospace;
    }
</style>

<h1>Погрешность интерполяции</h1><hr>
<form action="/interpolation/faults/results" method="post">
  Функция: <input type="text" name="function" value="${function}"><br><br>
  Размер таблицы интерполяции: <#list tableSizes as size>
    <div>
      <label>
        <input type="radio" name="tableSize" value="${size}" <#if size_index == 0>checked = "checked"</#if>>
      </label>${size}
    </div>
  </#list>
  <br>
  <input type="submit" value="Рассчитать">
</form>
</body>

</html>