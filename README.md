# Simulation of Life

An extension the Game of Life simulation to make it more interesting.

- DUE DATE: 01/03/2024
- AUTHORS: Waseef Khan, Izzie Yip

## Base Tasks

This coursework allows you to have a lot of flexibility on what you deliver — be creative!

- The base code comes with a simple life form, the Mycoplasma. Your first task is to make it more interesting. Modify its rule set so that it behaves in the following manner:
  - If the cell has fewer than two live neighbours it will die
  - If the cell has two or three live neighbours it will live on to the next generation
  - If the cell has more than three neighbours it will die
  - Lastly, any dead cell will come alive if it has exactly three neighbours

<strong>It’s important to note that all cells determine their next state prior to the transition to their new state. </strong>

- Using the Mycoplasma as an example, develop at least two new forms of life. Their rule set should be different from the Mycoplasma.
  - At least 1 life form should incorporate colour in its rule set, i.e., its colour may change while alive
  - At least 1 life form should exhibit different behaviours as time progresses

## Challenge Tasks

Once you have finished the base tasks, implement one or more challenge tasks. You can either choose from the following suggestions, or invent your own. You will be graded on a maximum of four challenge tasks.

- Non-deterministic cells. Cells execute the same set of rules during in generation. Implement a life form that behaves in a non-deterministic manner. That is, given a set of rules, the cell will execute a rule, Rn, with probability Pn.

- Symbiosis. Symbiosis is any long-term relationship between two different organisms. Implement two forms of life that either have a mutualistic or parasitic relationship. In a mutualistic relationship both life forms benefit while in a parasitic relationship only one benefits (the other is harmed).

- Disease. Modify the code so that some cells are occasionally infected with disease. There should be two elements to the disease:
  - it can spreads from one cell to its neighbours and
  - once in a diseased state the cell’s behaviour changes.
