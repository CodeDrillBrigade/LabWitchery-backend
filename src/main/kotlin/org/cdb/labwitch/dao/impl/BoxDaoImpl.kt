package org.cdb.labwitch.dao.impl

import com.mongodb.client.model.Filters.eq
import org.cdb.labwitch.components.DBClient
import org.cdb.labwitch.dao.BoxDao
import org.cdb.labwitch.models.Box
import org.cdb.labwitch.models.types.EntityId

class BoxDaoImpl(client: DBClient) : BoxDao(client) {
    override suspend fun get(id: EntityId): Box? {
        return get(eq("_id", id.id))
    }

    override suspend fun getByMaterial(materialName: String): Box? {
        return get(eq(Box::materialName.name, materialName))
    }
}
