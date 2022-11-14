<!doctype html>
<html>
<head>
  <title>cm-course</title>
</head>

<body>
<h1>Результаты</h1>

<table border="2" class="images">
  <tr>
    <th>Function</th>
    <th>Interpolation</th>
    <th>Faults</th>
  </tr>
    <#list encodedGraphicImagesPacks as pack>
      <tr>
        <th><img src="${pack.func}" alt="blank"></th>
        <th><img src="${pack.interpolate}" alt="blank"></th>
        <th><img src="${pack.faults}" alt="blank"></th>
      </tr>
    </#list><hr>
</table>

<form action="/interpolation/faults" method="get">
  <input type="submit" value="Назад">
</form>

</body>
</html>