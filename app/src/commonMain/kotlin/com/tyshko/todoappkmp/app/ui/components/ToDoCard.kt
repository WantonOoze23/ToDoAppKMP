package com.tyshko.todoapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tyshko.domain.model.ToDoModel
import androidx.compose.material3.*
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.tyshko.todoapp.ui.theme.Completed
import com.tyshko.todoapp.ui.theme.NotCompleted
import com.tyshko.todoappkmp.app.ui.theme.Height
import com.tyshko.todoappkmp.app.ui.theme.Padding


@Composable
fun ToDoCard(
    modifier: Modifier = Modifier,
    todo: ToDoModel,
    onEdit: (Long) -> Unit = {},
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Padding.simplePadding),
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isCompleted) Completed else NotCompleted
        ),
    ) {
        Column(modifier = Modifier.padding(Padding.mediumPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(Height.smallHeight))
                    Text(
                        text = todo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = onCheckedChange,
                    Modifier.semantics {
                        contentDescription = "CheckBox${todo.id}"
                    }
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    modifier = modifier.clickable {
                        onEdit.invoke(todo.id)
                    },
                    contentDescription = "Edit Todo",
                )
            }
        }
    }
}