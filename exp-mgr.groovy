def readExpenses =
{
  def expFile = new File("expenses.csv")
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

def listExpenses =
{ expenses ->
  def max = expenses.max()
  def format = java.text.NumberFormat.getCurrencyInstance()
  def maxRep = format.format(max)
  int len = maxRep.length()
  expenses.each { println(String.format("%${len}s".toString(), format.format(it))) }
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
}
reader.close()