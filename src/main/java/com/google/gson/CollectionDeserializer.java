package com.google.gson;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class CollectionDeserializer implements JsonDeserializer, InstanceCreator {
   private Collection constructCollectionType(Type collectionType, JsonDeserializationContext context) {
      JsonDeserializationContextDefault contextImpl = (JsonDeserializationContextDefault)context;
      ObjectConstructor objectConstructor = contextImpl.getObjectConstructor();
      return (Collection)objectConstructor.construct(collectionType);
   }

   public Collection createInstance(Type type) {
      return new LinkedList();
   }

   public Collection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      if (json.isJsonNull()) {
         return null;
      } else {
         Collection collection = this.constructCollectionType(typeOfT, context);
         Type childType = (new TypeInfoCollection(typeOfT)).getElementType();
         if (json.isJsonArray()) {
            Iterator var6 = json.getAsJsonArray().iterator();

            while(var6.hasNext()) {
               JsonElement childElement = (JsonElement)var6.next();
               if (childElement != null && !childElement.isJsonNull()) {
                  Object value = context.deserialize(childElement, childType);
                  collection.add(value);
               }
            }
         } else {
            Object value = context.deserialize(json, childType);
            collection.add(value);
         }

         return collection;
      }
   }
}
