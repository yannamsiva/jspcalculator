<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Calculator</title>
</head>
<body>
    <h1 style="text-align:center;">Calculator</h1>

    <form action="Calculator" method="get">
        <label>First number:</label>
        <input type="text" name="n1" required />
        <br /><br />

        <label>Second number:</label>
        <input type="text" name="n2" required />
        <br /><br />

        <div>
            <label>
                <input type="radio" name="operation" value="add" required /> Addition
            </label><br />
            <label>
                <input type="radio" name="operation" value="sub" /> Subtraction
            </label><br />
            <label>
                <input type="radio" name="operation" value="mul" /> Multiplication
            </label><br />
        </div>

        <br />
        <input type="submit" value="Calculate" />
    </form>
</body>
</html>
