package org.cdb.labwitch.dao.impl

import com.mongodb.client.model.Filters.eq
import org.cdb.labwitch.components.DBClient
import org.cdb.labwitch.dao.UserDao
import org.cdb.labwitch.models.User

class UserDaoImpl(
    client: DBClient
) : UserDao(client) {
    override suspend fun get(id: String): User? = get(eq(User::id.name, id))
}