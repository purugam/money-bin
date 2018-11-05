def f = java.text.NumberFormat.getNumberInstance()
f.setMinimumFractionDigits(2)
f.setMaximumFractionDigits(2)
def expFile = new File("expenses.csv")

def readExpenses =
{
  def expReader = new BufferedReader(new FileReader(expFile))
  def expLine = expReader.readLine()
  def expenses = []
  while(expLine)
  {
    expenses.add(Float.parseFloat(expLine))
    expLine = expReader.readLine()
  }
  return expenses
}

def writeExpenses =
{
  expenses ->
  def writer = new BufferedWriter(new FileWriter(expFile))
  expenses.collect({ e -> "$e" }).each { writer.write(it + "\r\n") }
  writer.close()
}

def listExpenses =
{ expenses ->
  def max = expenses.max()
  def maxRep = f.format(max)
  int len = maxRep.length()
  String template = "%${len}s".toString()
  expenses.each { println(String.format(template, f.format(it))) }
}

def addExpense =
{
  expenses, line ->
  def value = f.parse(line.trim())
  expenses.add(value)
  println "  Ausgabe hinzugefügt: ${f.format(value)}."
}

def expenses = readExpenses()
def reader = new BufferedReader(new InputStreamReader(System.in))
def line = "start"
while(line && line != "exit")
{
  print "> "
  line = reader.readLine()
  if(line == "list")
    listExpenses(expenses)
  if(line.startsWith("add "))
    addExpense(expenses, line.substring(4))
}
reader.close()
writeExpenses(expenses)