/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Group;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class GroupComparator<T>
implements Comparator<T> {
    public static final GroupComparator<Group> CN = new GroupComparator<Group>(){

        @Override
        public int compare(Group _ug1, Group _ug2) {
            return STRING_CN.compare(_ug1.getCn(), _ug2.getCn());
        }
    };
    public static final GroupComparator<List<Group>> LIST_CN = new GroupComparator<List<Group>>(){

        @Override
        public int compare(List<Group> _l1, List<Group> _l2) {
            int result = 0;
            if ((_l1 == null || _l1.size() == 0) && _l2 != null && _l2.size() > 0) {
                result = -1;
            } else if (_l1 != null && _l1.size() > 0 && (_l2 == null || _l2.size() == 0)) {
                result = 1;
            } else if (_l1 != null && _l2 != null) {
                for (int i = 0; result == 0 && i < _l1.size() && i < _l2.size(); ++i) {
                    result = CN.compare(_l1.get(i), _l2.get(i));
                }
                if (result == 0 && _l1.size() != _l2.size()) {
                    result = _l1.size() < _l2.size() ? -1 : 1;
                }
            }
            return result;
        }
    };
    private static final String REGEX_NUMBER = "\\d.*";
    public static final GroupComparator<String> STRING_CN = new GroupComparator<String>(){

        @Override
        public int compare(String _ug1, String _ug2) {
            int result = 0;
            if (_ug1 == null && _ug2 != null) {
                result = -1;
            } else if (_ug1 != null && _ug2 == null) {
                result = 1;
            } else if (_ug1 != null && _ug2 != null) {
                Object[] cn1 = splitCn(_ug1);
                Object[] cn2 = splitCn(_ug2);
                for (int i = 0; result == 0 && i < cn1.length && i < cn2.length; ++i) {
                    if (cn1[i] instanceof Integer && cn2[i] instanceof String) {
                        result = -1;
                        continue;
                    }
                    if (cn1[i] instanceof String && cn2[i] instanceof Integer) {
                        result = 1;
                        continue;
                    }
                    if (cn1[i] instanceof Integer && cn2[i] instanceof Integer) {
                        result = ((Integer)cn1[i]).compareTo((Integer)cn2[i]);
                        continue;
                    }
                    if (!(cn1[i] instanceof String) || !(cn2[i] instanceof String)) continue;
                    result = ((String)cn1[i]).compareTo((String)cn2[i]);
                }
                if (result == 0 && cn1.length != cn2.length) {
                    result = cn1.length < cn2.length ? -1 : 1;
                }
            }
            return result;
        }
    };

    static Object[] splitCn(String _cn) {
        String cn = _cn;
        ArrayList<Object> result = new ArrayList<Object>();
        StringBuilder content = new StringBuilder();
        while (cn.length() > 0) {
            if (content.length() == 0) {
                content.append(cn.charAt(0));
                cn = cn.substring(1);
                continue;
            }
            if (cn.matches(REGEX_NUMBER)) {
                if (!content.substring(content.length() - 1).matches(REGEX_NUMBER)) {
                    result.add(content.toString());
                    content.delete(0, content.length());
                }
                content.append(cn.charAt(0));
                cn = cn.substring(1);
                continue;
            }
            if (content.substring(content.length() - 1).matches(REGEX_NUMBER)) {
                result.add(Integer.parseInt(content.toString()));
                content.delete(0, content.length());
            }
            content.append(cn.charAt(0));
            cn = cn.substring(1);
        }
        if (content.substring(content.length() - 1).matches(REGEX_NUMBER)) {
            result.add(Integer.parseInt(content.toString()));
        } else {
            result.add(content.toString());
        }
        return result.toArray(new Object[result.size()]);
    }

}

