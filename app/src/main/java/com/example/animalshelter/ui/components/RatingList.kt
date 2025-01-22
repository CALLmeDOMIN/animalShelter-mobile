package com.example.animalshelter.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animalshelter.model.Rating
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RatingList(ratings: Set<Rating>?) {
    val safeRatings = ratings ?: emptySet()

    fun getYearAndMonth(dateString: String): Pair<Int, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSS]")
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val month = String.format("%02d", dateTime.monthValue)
        return Pair(dateTime.year, month)
    }

    safeRatings.forEach { rating ->
        val (year, month) = getYearAndMonth(rating.ratingDate)
        val dateText = "$year-$month"

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Rating",
                tint = Color.Yellow,
            )
            Text(
                text = "${rating.value} / 5",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = rating.comment,
                modifier = Modifier.weight(3f)
            )
            Text(
                text = dateText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}