def f = java.text.NumberFormat.getNumberInstance()
f.setMinimumFractionDigits(2)
f.setMaximumFractionDigits(2)
def expFile = new File("expenses.csv")
def expenses = []

def readExpenses =
{
  if(!expFile.canRead())
    return []
  def expReader = new BufferedReader(new FileReader(expFile))
  def expLine = expReader.readLine()
  def expensesRead = []
  while(expLine)
  {
    expensesRead.add([amount: Float.parseFloat(expLine)])
    expLine = expReader.readLine()
  }
  return expensesRead
}

def writeExpenses =
{
  def writer = new BufferedWriter(new FileWriter(expFile))
  expenses.collect({ e -> "${e.amount}" }).each { writer.write(it + "\r\n") }
  writer.close()
}

def listExpenses =
{ 
  if(!expenses)
    return
  def max = expenses.collect({ it.amount }).max()
  def maxRep = f.format(max)
  int len = maxRep.length()
  String template = "%s %${len}s".toString()
  expenses.each { println(String.format(template, "${it.key}", f.format(it.amount))) }
}

def addExpense =
{
  line ->
  def value = f.parse(line.trim())
  def expense = [ amount:value, key:"$value".sha256().substring(0, 7) ]
  expenses.add(expense)
  println "  Ausgabe hinzugefügt: ${expense}."
}

expenses = readExpenses()
def reader = new BufferedReader(new InputStreamReader(System.in))
def line = "start"
while(line && line != "exit")
{
  print "> "
  line = reader.readLine()
  if(line == "list")
    listExpenses()
  if(line.startsWith("add "))
    addExpense(line.substring(4))
}
reader.close()
// writeExpenses(expenses)