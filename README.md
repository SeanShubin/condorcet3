# Condorcet voting method

## Goal
- Determine the preference of a group of eligible voters, both accurately and verifiably
    - Allow voters to express their entire preference
    - If a candidate would win a two-candidate election against each of the other candidates in a plurality vote, that candidate must be the winner.
    This is known as the "Condorcet Winner Criterion".
    - Publish enough information so that any voter can verify the results.
    If it is a secret ballot vote, individuals will not be able to verify the votes of others, but they can still detect if their vote was hijacked.

### Important, but out of scope to the voting system itself
- Who is allowed to vote
- How candidates are selected
- What to do in the event of a tie
- What to do if fraudulent ballots are detected
- What to do if the tally is discovered to be incorrect

## Interacting with this tool
These are recommendations for how a user interface should interact with this tool.

### Capturing individual preference
- The voter is able to specify all of their preferences, not just their top candidate
    - This can be done by assigning a “rank” to each candidate, where 1 is their most preferred, 2 is their second most preferred, and so on.
- The voter is able to give the same rank to multiple candidates
    - This allows the voter to signify no preference between a group of candidates, while still expressing the ranking of that group in relation to other candidates.
- The voter is able to express which candidates they are voting against
    - This can be done by having a "none-of-these" entry they can rank in relation to the other candidates
    - While in practice it may be necessary to elect a candidate below "none-of-these" should "none-of-these" win, it is still useful to capture the fact that the voters did not have any choices they approved of.  This situation may indicate a problem with the candidate selection process, or the need to support write-in candidates.
- The voter should be able to cast a vote with no rankings
    - This allows for telling the difference between “no preference at all”, and “preference is unknown”.
- Any unranked candidates are considered lower rank than ranked candidates
- All unranked candidates are considered ties with each other

### Mechanics
- Each voter is given a voter id.
    - This is attached to their ballot so that the voter cannot vote twice
- Upon voting, each voter is given a secret ballot identifier used to identify which ballot is theirs
    - A version 4 UUID is suitable for this purpose
    - The voter should get a different secret ballot identifier for each election they participate in
- After the votes are tallied, the following information is made public
    - The list of eligible voters
    - The eligible voters who did not vote
    - The actual votes, using the secret ballot identifier instead of the voter id
    - (optional) Intermediate calculations for the tally, so that if a technically minded person arrives at a different result, it is easier for them to determine if the flaw is in the voting system or their own calculations.
    - (optional, and only if not secret ballot) The actual ballots, including the voter id, can be made public.  This may be useful for practice runs used to test the voting system, or in representative voting situations where it is necessary to hold voters accountable to those they represent.
- The secret ballot identifier and actual votes, allows each voter to confirm that their vote was cast correctly, without revealing their identity.
- The list of eligible voters.  This allows someone who thought they were eligible to vote, to detect that their vote was not counted
- The list of eligible voters who did not vote.  This allows those who did not vote, to detect if a vote was cast in their name
- The “schulze method” is one kind of Condorcet method, this will be used to resolve circular ambiguities (refer to example below)

## Examples

### Why Condorcet methods are superior to first past the post
Lets say you have 3 candidates, lets call them "minor-improvements", "status-quo", and "radical-changes".

    30% of the population prefers minor-improvements, then status-quo, then radical-changes
    30% of the population prefers status-quo, then minor-improvements, then radical-changes
    40% of the population prefers radical-changes, then minor-improvements, then status-quo

If you are only counting who has the most first place votes, radical-changes would win, even though this does not accurately reflect voter preference.
To see why consider this:

    70% of the voters would rather have minor-improvements than status-quo
    60% of the voters would rather have minor-improvements than radical-changes
    60% of the voters would rather have status-quo than radical-changes

When the candidates are compared in pairs, it is obvious the accurate voter preference is minor-improvements, then status-quo, then radical-changes.
But when only the voter's top most candidate is considered, we have thrown away so much information that the least preferred candidate, radical-changes, actually wins.
In a Condorcet method, the candidates in this same situation would be ranked minor-improvements, then status-quo, then radical-changes, which is consistent with the actual voter preference.

[Full Example](jvm-backend/src/test/resources/test-data/01-contrast-first-past-the-post)

### How Condorcet methods reduce tactical voting
Consider this dilemma

    3 voters prefer minor-improvements, then status-quo, then radical-changes
    4 voters prefer status-quo, then minor-improvements, then radical-changes
    2 voters prefer radical-changes, then minor-improvements, then status-quo

If we are counting the most first place votes or doing instant runoff voting, although the 2 voters prefer radical-changes, they don't dare vote that way because that would throw the election to status-quo rather than minor-improvements.
It is in the best interest of the 2 voters to not express their preference accurately.
This unfairly misrepresents the number of voters who actually preferred radical-changes, as that information about their preference was lost.
A Condorcet method would compare the candidates in pairs, like so:

    minor-improvements defeats status-quo 5 to 4
    minor-improvements defeats radical-changes 7 to 2
    status-quo defeats radical-changes 7 to 2

So the ranking becomes minor-improvements, then status-quo, then radical-changes.
In a Condorcet method, the 2 voters could accurately express their vote, without sabotaging their own interests by causing status-quo to defeat minor-improvements.

[Full Example](jvm-backend/src/test/resources/test-data/02-reduce-tactical-voting)

### Why Condorcet methods are superior to instant runoff voting
Instant runoff voting does not meet condorcet criteria, it works like this:

> Ballots are initially counted for each elector's top choice, losing candidates are eliminated, and ballots for losing candidates are redistributed until one candidate is the top remaining choice of a majority of the voters.

The problem here is that since only the top choices are counted each round, candidates closer to the consensus preference can be the first ones eliminated.
To illustrate this, consider a contrived case with 10 candidates.
Each voter has a relatively unknown preference.
If they could not have their own top choice, each voter would rather have "satisfactory" instead another voter's top choice

    2 voters prefer "only-liked-by-two"   to "satisfactory"
    1 voters prefer "a-only-liked-by-one" to "satisfactory"
    1 voters prefer "b-only-liked-by-one" to "satisfactory"
    1 voters prefer "c-only-liked-by-one" to "satisfactory"
    1 voters prefer "d-only-liked-by-one" to "satisfactory"
    1 voters prefer "e-only-liked-by-one" to "satisfactory"
    1 voters prefer "f-only-liked-by-one" to "satisfactory"
    1 voters prefer "g-only-liked-by-one" to "satisfactory"
    1 voters prefer "h-only-liked-by-one" to "satisfactory"

In both instant runoff and first past the post, the candidate "only-liked-by-two" wins, even though 80% of the voters prefer "satisfactory" to "only-liked-by-two".
This is because "satisfactory", the one every single voter preferred over someone else's top candidate, was the first one eliminated.

[Full Example](jvm-backend/src/test/resources/test-data/03-contrast-instant-runoff)

## More complicated examples
These examples address more esoteric aspects of the schulze method that only tend to occur in very unusual cases.
They are probably only useful for people intending to tally the ballots by hand.

### How circular ambiguities are resolved using the “schulze method”

Lets say you have 3 candidates, call them "rock", "paper", and "scissors".
Initially there are 9 votes, like so

    3 voters rank candidates in this order: rock, scissors, paper
    3 voters rank candidates in this order: paper, rock, scissors
    3 voters rank candidates in this order: scissors, paper, rock

This results in a 3 way tie.
Now what would you expect to happen if a 10th voter voted as follows?

    1 voter ranks candidates in this order: rock, scissors, paper

The obvious answer is that now we have enough information to break the tie, ending up with an outcome the same as the last ballot cast.
How this result can be computed is a little less obvious, consider this

    rock defeats scissors 7 to 3
    paper beats rock 6 to 4
    scissors defeats paper 7 to 3

We appear to be going in a circle, indicating all candidates are still tied.
This is why we need the schulze method to detect that the tie has actually been broken.
The algorithm is a bit complex, but here is how it works conceptually.

We figure out the "strongest path" for each pair of candidates.
The direct paths have the following strengths

    rock    -(7)-scissors = 7
    scissors-(3)-rock     = 3
    paper   -(6)-rock     = 6
    rock    -(4)-paper    = 4
    scissors-(7)-paper    = 7
    paper   -(3)-scissors = 3

However, if we consider the indirect paths, we get

    rock    -(4)-paper   -(3)-scissors = 3
    scissors-(7)-paper   -(6)-rock     = 6
    paper   -(3)-scissors-(3)-rock     = 3
    rock    -(7)-scissors-(7)-paper    = 7
    scissors-(3)-rock    -(4)-paper    = 3
    paper   -(6)-rock    -(7)-scissors = 6

The result number on the right is the minimum of the two numbers on the left, you are only as strong as your weakest link.
Now that we have we have two possible paths to each pairing, we take the strongest ones

    rock    -(7)-scissors              = 7
    scissors-(7)-paper   -(6)-rock     = 6
    paper   -(6)-rock                  = 6
    rock    -(7)-scissors-(7)-paper    = 7
    scissors-(7)-paper                 = 7
    paper   -(6)-rock    -(7)-scissors = 6

This results in the following

    rock defeats scissors 7 to 6
    rock beats paper 7 to 6
    scissors defeats paper 7 to 6

This finally gives us exactly the result we intuitively expected

    1st rock
    2nd scissors
    3rd paper

[Full Example](jvm-backend/src/test/resources/test-data/04-resolve-cycle-using-schulze-method)

### Verifying results against example on wikipedia

This example is the same one used on the [wikipedia](https://en.wikipedia.org/wiki/Schulze_method) page for the schulze method.
It illustrates a much more complicated cycle than the rock-paper-scissors example above.

[Full Example](jvm-backend/src/test/resources/test-data/05-schulze-example-from-wikipedia)
