def expenses = [
    [amount: -135.47, account:"LEBENSMITTEL", vendor:"Lidl", label:"Wocheneinkauf"],
    [amount: -200.00, account:"LEBENSMITTEL", vendor:"Lidl", label:"Wocheneinkauf"],
    [amount: -150.00, account:"FORTBEWEGUNG", vendor:"Shell", label:"Wocheneinkauf"],
]
def calculation = [cash: 0.0, bank: -485.47]
def calculate = { e, c ->
    def bins = e.groupBy({ it.account })
    bins.each { entry -> entry.value = entry.value.collect({it.amount}).sum() }
    println bins
    def have = bins.values().sum()
    def should = c.cash + c.bank
    def diff = should - have
    println "should: $should - have: $have = diff: $diff"
}
calculate(expenses, calculation)