**Test Coverage**

# What is Test Coverage?

Test Coverage reports on development carried out in salesforce which, are not covered by Tests.

The scope of this Test extends to checking custom attributes only.
Therefore any gaps identified in the report, are the minnimum gaps in relation to coverage

# Scope

Entities, which do not have Yaml Entries, for the following categories, are flagged in the error log and asserted on

- Data Types (Datatype, including field length)
- English Labels
- German Labels
- German Picklists
- Mandatory Fields (If Custom Fields are required as Mandatory, we check that they are mandatory)

**Coverage Reported by this Suite**
- Custom Entities
- Entity Fields

# Quality Control

The **Integrity of the Salesforce Test Function**, is dependent on all custom values being added to **Entitiy YAML Files** as entities are created or modified
during a sprint.

If NOT ALL Changes in relation to stories are entered into Yaml Files during a sprint, the results show a false
sense of quality in relation to the reports Often there are gaps in the DECLARATION OF EXPECTED RESULTS and this report
is intended, to reveal same. By following git flow guidelines one is better placed to ensure that changes, are submitted and approved 
quickly, with minimal adverse impact to other stories.

During a sprint the tester is responsible for;
- Ensuring that all changes are made on a git feature/scenario branch
- Tests are annotated with the Jira Story in a sprint. (At the end of a release, all issue tags are cleared)
- Changes are verified on a Jenkins Feature Pipeline before creating a pull request. 
This allows the Pull Request Viewer to identify possible gaps in the test results, isolated to the scenario in question.

_For example if SAL-333 relates to changes in relation to Account, Changes in relation to a new validation rule on another entity should not be part of that branch_


This **suite** should be run by the PULL REQUEST APPROVER before completing a merge in relation to Attribute/Entity Changes.
If there are coverae results outstanding the Approver will know that additional changes are due on the feature branch and **will not delete** it.
In any case the PULL REQUEST(PR) CREATOR, should mention if the current PR is the last for the scenario in the PR description. 
an>


**ToDo**

Coverage Tests have yet to be completed for custom;
- Profiles
- Roles
- Permission Sets
- Public Groups
- Sharing Rules
- Global Picklists
- Validation Rules

An Entity Field Usage Report should also be used to reveal fields on entities which are not used. These should be removed

- Field Descriptions Play an important role in helping us to indentify the purpose and relationships fields have, 
All custom attributes should have field descriptions


