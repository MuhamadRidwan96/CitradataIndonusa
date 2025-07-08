package com.example.core_ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CompactSearchableDropdown(
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T?) -> Unit,
    itemLabel: (T) -> String,
    modifier: Modifier = Modifier,
    label: String = "Pilih Item",
    placeholder: String = "Cari...",
    emptyText: String = "Item tidak ditemukan",
    maxDropdownHeight: Dp = 300.dp,
    maxVisibleItems: Int = 5
) {

    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Calculate dynamic height for dropdown content
    val dropdownContentHeight = remember {
        (48.dp * maxVisibleItems).coerceAtMost(maxDropdownHeight)
    }

    LaunchedEffect(selectedItem) {
        searchQuery = selectedItem?.let(itemLabel) ?: ""
    }

    LaunchedEffect(expanded) {
        if (expanded) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    val filteredItems by remember(searchQuery, items) {
        derivedStateOf {
            if (searchQuery.isEmpty()) items
            else items.filter { itemLabel(it).contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (!expanded) expanded = true
                },
                label = { Text(label, fontSize = 12.sp) },
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth()
                    .heightIn(min = 48.dp, max = 48.dp)
                    .focusRequester(focusRequester),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true,
                placeholder = { Text(placeholder, fontSize = 12.sp) },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .exposedDropdownSize(matchTextFieldWidth = true)
            ) {
                // Fixed height container for scrollable content
                Box(
                    modifier = Modifier
                        .height(dropdownContentHeight)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        // Clear option
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Kosongkan Pilihan",
                                    fontSize = 12.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            onClick = {
                                onItemSelected(null)
                                searchQuery = ""
                                expanded = false
                            }
                        )

                        // Items list
                        if (filteredItems.isEmpty()) {
                            Text(
                                text = emptyText,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        } else {
                            filteredItems.forEach { item ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = itemLabel(item),
                                            fontSize = 12.sp,
                                            modifier = Modifier.fillMaxWidth(),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    onClick = {
                                        onItemSelected(item)
                                        searchQuery = itemLabel(item)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
