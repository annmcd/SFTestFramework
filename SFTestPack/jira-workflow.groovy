/**
 * When Current State is X
 * test result should change status to
 *
 * when'Open',{'success' should:'Resolve Issue'}when'Reopened',{'success' should:'Resolve Issue'}when'Resolved',{'failure' should:'Reopen Issue'}when'In Progress',{'success' should:['Stop Progress','Resolve Issue']}when'Closed',{'failure' should:'Reopen Issue'}//ToDo implement a workflow based on something similar to the above  its more fitting. below represents current model

 */



when 'TO DO', {
    'success' should: 'Done'
}
when 'Ready for QA', {
    'success' should: 'Done'
}
when 'Done', {
    'failure' should: 'QA Failed'
}
when 'QA Failed', {
    'success' should: ['Done', 'TO DO']
}
when 'In Progress', {
    'success' should: 'Ready for UAT'
}

when 'In Progress', {
    'fail' should: 'QA Failed'
}