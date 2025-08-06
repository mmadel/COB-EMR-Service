package com.cob.emr.usecases.medical.note;

import com.cob.emr.model.medical.note.soap.field.Item;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenerateSOAPFieldUseCase {


    public Item buildTree(List<String[]> dependencies) {
        Item root = new Item(dependencies.get(0)[0]); // Root node
        Map<String, Item> nodeMap = new HashMap<>();
        nodeMap.put(root.fieldName, root);

        for (String[] path : dependencies) {
            Item parent = root;
            for (int i = 1; i < path.length; i++) {
                String fieldName = path[i];

                // Check if node exists
                Item current = nodeMap.getOrDefault(fieldName, new Item(fieldName));

                // Attach to parent if not already added
                if (!parent.dependencies.contains(current)) {
                    parent.dependencies.add(current);
                    nodeMap.put(fieldName, current);
                }

                parent = current; // Move down the hierarchy
            }
        }

        return root;
    }
}
