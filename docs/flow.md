# Flow

## Entities
- login
    - email
    - password
- register
    - email
    - password
    - confirm-password
- elections
    - list of election names
- voters
    - list of voters
- election
    - is open
    - list of voters (valid for that election)
    - list of candidates
- ballot
    - voter
    - election
    - list of ranked candidates
- tally
- your-ballots
    - list of ballots (yours, for open election)

## Events

### Guest
- register(email, password)

### User
- create-election(electionNam, voter)
- cast-ballot(electionName, voter, rankings)
- abstain-ballot(electionName, voter)
- reset-ballot(electionName, voter)

### Unopened Election Owner
- set-voters(electionName, voters)
- set-candidates(electionName, candidates)
- open-election(electionName)

### Opened Election Owner
- close-election(electionName)

### Admin
- remove-election(electionName)
- reopen-election(electionName)
- reset-election(electionName)
- unregister-voter(voter)